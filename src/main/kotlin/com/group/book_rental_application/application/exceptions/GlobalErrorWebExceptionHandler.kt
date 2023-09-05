package com.bnf.xc.prepayment.core.application.exceptions

import com.bnf.xc.prepayment.core.application.exceptions.handler.DefaultExceptionHandler
import com.bnf.xc.prepayment.core.application.exceptions.handler.FeignExceptionHandler
import com.group.book_rental_application.domain.exceptions.BookRentNotFoundIllegalException
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException
import reactor.core.publisher.Mono

/**
 * 전역 에러 핸들링
 * @author lockon2200
 * @since 2022-12-13
 */

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer,
    val defaultExceptionHandler: DefaultExceptionHandler,
    val feignExceptionHandler: FeignExceptionHandler
) : AbstractErrorWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext) {

    private val logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler::class.java)

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::handler)
    }

    fun handler(request: ServerRequest): Mono<ServerResponse> {
        val attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        return when (val e = super.getError(request)) {
            is FeignException -> {
                feignExceptionHandler.handler(e, attributes)
            }
            is BookRentNotFoundIllegalException -> {
                ServerResponse.status(HttpStatus.NOT_FOUND)
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.message ?: "Not Found Exception",
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }
            is NotFoundException -> {
                ServerResponse.status(HttpStatus.NOT_FOUND)
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.message ?: "Not Found Exception",
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }
            is ResponseStatusException -> {
                ServerResponse.status(attributes["status"].toString().toInt())
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.reason ?: "Response Status Exception",
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }
            is NoSuchElementException -> {
                ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .bodyValue(
                        ErrorResponse(
                            code = attributes["code"].toString(),
                            message = e.message ?: "No Such Element Exception",
                            traceId = attributes["traceId"].toString()
                        )
                    )
            }
            else -> {
                defaultExceptionHandler.handler(attributes)
            }
        }
    }
}
