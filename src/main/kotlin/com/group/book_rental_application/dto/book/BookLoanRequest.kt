package com.group.book_rental_application.dto.book

data class BookLoanRequest(
    val userId: String,
    val bookId: String
)