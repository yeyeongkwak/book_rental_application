package com.group.book_application.domain.model

import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table(name = "book_rental.book")
data class Book(
    @Id
    val bookId: String,

    val bookName: String,

    val publishDate: LocalDate,

    val purchaseDate: LocalDate,

    val availableDays: Int = 14,

    var author: String? = null,

    var status: BookStatusType = BookStatusType.AVAILABLE,

    var availableRank: AvailableBookType = AvailableBookType.ALL

) {
    fun rent(){
        status=BookStatusType.RENT
    }

    fun returnBooks(){
        status=BookStatusType.AVAILABLE
    }

    fun delayReturnBooks(){
        status=BookStatusType.DELAYED
    }

}