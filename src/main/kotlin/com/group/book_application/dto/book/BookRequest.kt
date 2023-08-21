package com.group.book_application.dto.book

import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime
import java.util.*

data class BookRequest (
    val bookId:String,
    var bookName:String,
    var publishDate: LocalDateTime,
    var purchaseDate:LocalDateTime,
    var author:String?,
    var availableDays:Int=14,
    @Enumerated(EnumType.STRING)
    var status:BookStatusType,
    @Enumerated(EnumType.STRING)
    var availableRank:AvailableBookType=AvailableBookType.ALL

    )
