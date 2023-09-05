package com.group.book_rental_application.application.dto

data class PageableTotalDTO<T>(
    val content: T,
    val totalPages: Int,
    val totalElements: Long
)