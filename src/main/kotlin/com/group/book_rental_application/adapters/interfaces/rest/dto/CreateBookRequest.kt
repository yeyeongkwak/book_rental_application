package com.group.book_rental_application.adapters.interfaces.rest.dto

import com.group.book_rental_application.domain.enums.AvailableBookType
import io.swagger.v3.oas.annotations.media.Schema

data class CreateBookRequest(
    @field:Schema(description = "책 이름")
    val bookName: String,
    @field:Schema(description = "출판일")
    val publishDate: String,
    @field:Schema(description = "구매일")
    val purchaseDate: String,
    @field:Schema(description = "대여 가능일")
    val availableDays: Int,
    @field:Schema(description = "작가")
    val author: String? = null,
    @field:Schema(description = "대여 가능 등급")
    val availableRank: AvailableBookType = AvailableBookType.ALL
)
