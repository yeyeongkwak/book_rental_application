package com.group.book_rental_application.application.exceptions.handler

import com.group.book_rental_application.application.exceptions.ErrorResponse
import com.group.book_rental_application.domain.exceptions.MemberNotFoundIllegalException
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

/**
 * 기본 예외 처리 핸들러
 * @author lockon2200
 * @since 2022-12-13
 */

@Component
class DefaultExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer,
) : AbstractErrorWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext) {

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }
//    fun handler(attributes: Map<String, Any>): Mono<ServerResponse> {
//        val attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults())
//        return ServerResponse.status(attributes["status"].toString().toInt())
//            .bodyValue(
//                ErrorResponse(
//                    code = attributes["code"].toString(),
//                    message = e.message?:attributes["message"].toString(),
//                    traceId = attributes["traceId"].toString()
//                )
//            )
//    }

    fun handler(request: ServerRequest): Mono<ServerResponse> {
        val attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val e = super.getError(request)
        return when (val e = super.getError(request)) {
            is MemberNotFoundIllegalException -> {
                ServerResponse.status(HttpStatus.NOT_FOUND)
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.message ?: "Not Found Exception",
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }

            else -> {
                ServerResponse.status(attributes["status"].toString().toInt())
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.message ?: attributes["message"].toString(),
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }
        }

    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::handler)
    }
}