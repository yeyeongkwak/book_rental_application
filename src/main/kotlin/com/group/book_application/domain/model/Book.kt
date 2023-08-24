package com.group.book_application.domain.model

import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table(name = "book_rental.book")
data class Book(
    @Id
    val bookId:String,

    var bookName:String,

    var publishDate:LocalDate,

    val purchaseDate:LocalDate,

    var availableDays:Int,

    var author:String?,

    var status: String,

    var availableRank: String

) {
}