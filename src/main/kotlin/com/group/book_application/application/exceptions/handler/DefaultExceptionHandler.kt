package com.bnf.xc.prepayment.core.application.exceptions.handler

import com.bnf.xc.prepayment.core.application.exceptions.ErrorResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * 기본 예외 처리 핸들러
 * @author lockon2200
 * @since 2022-12-13
 */

@Component
class DefaultExceptionHandler {

    fun handler(attributes: Map<String, Any>): Mono<ServerResponse> {
        return ServerResponse.status(attributes["status"].toString().toInt())
            .bodyValue(
                ErrorResponse(
                    code = attributes["code"].toString(),
                    message = attributes["message"].toString(),
                    traceId = attributes["traceId"].toString()
                )
            )
    }
}