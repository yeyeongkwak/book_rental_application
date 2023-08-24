package com.group.book_application.application.dto

import java.time.LocalDate

data class BookDTO(
    val bookId:String,
    val bookName:String,
    val publishDate:LocalDate,
    val purchaseDate:LocalDate,
    val availableDays:Int,
    val author:String,
    val status:String,
    val availableRank:String

)