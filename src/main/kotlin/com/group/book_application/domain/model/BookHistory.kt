package com.group.book_application.domain.model

import com.group.book_application.domain.enums.BookHistoryStatusType
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

class BookHistory(
    @Id
    val bookHistoryId:String,
    val rentDate:LocalDateTime,
    val returnDate:LocalDateTime,
    var leftDate:Int,
    var status: BookHistoryStatusType,
    val bookId:String,
    val userId:String,
) {
}