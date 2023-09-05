package com.bnf.xc.prepayment.core.application.exceptions.handler

import com.bnf.xc.prepayment.core.application.exceptions.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.FeignException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.nio.ByteBuffer
import java.util.*

/**
 * FeignClient 에러 핸들링
 * >네트워크 연결, 코어 서비스 에러를 변환 및 전달
 * @author lockon2200
 * @since 2022-12-13
 */

@Component
class FeignExceptionHandler {

    fun handler(e: FeignException, attributes: Map<String, Any>): Mono<ServerResponse> {
        return ServerResponse
            .status(e.status())
            .bodyValue(
                try {
                    toErrorResponse(toBodyString(e.responseBody()))
                } catch (e: Exception) {
                    ErrorResponse(
                        code = attributes["code"].toString(),
                        message = attributes["message"].toString(),
                        traceId = attributes["traceId"].toString()
                    )
                }
            )
    }

    private fun toBodyString(responseBody: Optional<ByteBuffer>): String {
        return if (responseBody.isPresent) {
            String(responseBody.get().array())
        } else {
            ""
        }
    }

    private fun toErrorResponse(body: String): ErrorResponse {
        return jacksonObjectMapper().readValue(body, ErrorResponse::class.java)
    }
}