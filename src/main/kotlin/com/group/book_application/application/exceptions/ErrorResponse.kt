package com.bnf.xc.prepayment.core.application.exceptions

/**
 * X-Commerce 표준 에러 모델
 * @author lockon2200
 * @since 2022-12-13
 */

data class ErrorResponse(
    val code: String,
    val message: String,
    val traceId: String
)
