package com.group.book_application.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
open class BookRentIllegalArgumentException(message: String) : IllegalArgumentException(message)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
open class BookRentIllegalStateException(message: String) : IllegalStateException(message)

@ResponseStatus(value = HttpStatus.NOT_FOUND)
open class BookRentNotFoundIllegalException(message: String) : IllegalArgumentException(message)

/**
 * 발송 정보를 찾을 수 없음
 */
class BookRentNotFoundException(message: String) : BookRentNotFoundIllegalException(message)