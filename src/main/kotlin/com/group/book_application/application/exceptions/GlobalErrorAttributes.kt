package com.bnf.xc.prepayment.core.application.exceptions

import brave.Tracer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * Custom 에러 처리를 위해 DefaultErrorAttributes 재정의
 * @author lockon2200
 * @since 2022-12-13
 */

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {

    /**
     * default attribute에 code, traceId 추가
     */
    private val defaultServerErrorCode = "S_PPM_0000"

    @Autowired
    lateinit var tracer: Tracer

    override fun getErrorAttributes(request: ServerRequest?, options: ErrorAttributeOptions?): MutableMap<String, Any> {
        val defaultAttributes = super.getErrorAttributes(request, options)
        //val status = defaultAttributes["status"].toString()
        defaultAttributes["code"] = defaultServerErrorCode
        defaultAttributes["traceId"] = tracer.currentSpan().context().traceIdString()
        return defaultAttributes
    }
}