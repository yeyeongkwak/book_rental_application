package com.group.book_application.domain.model

import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "book")
data class Book(
    @Id
    val bookId:String,

    val bookName:String,

    val publishDate:LocalDateTime,

    val purchaseDate:LocalDateTime,

    var availableDays:Int,

    val author:String?,

    var status: BookStatusType,

    var availableRank: AvailableBookType

) {
}