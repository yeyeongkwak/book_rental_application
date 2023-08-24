package com.group.book_application.adapters.interfaces.rest.dto

import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class CreateBookRequest(
    @field:Schema(description = "책 이름")
    var bookName:String,
    @field:Schema(description = "출판일")
    var publishDate:String,
    @field:Schema(description = "구매일")
    var purchaseDate:String,
    @field:Schema(description = "대여 가능일")
    var availableDays:Int,
    @field:Schema(description = "작가")
    var author:String,
//    @field:Schema(description = "책 대여상태")
//    var status:BookStatusType,
    @field:Schema(description = "대여 가능 등급")
    var availableRank:String
)
