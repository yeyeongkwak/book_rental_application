package com.group.book_rental_application.application.dto

import com.group.book_rental_application.domain.enums.RentHistoryStatusType
import java.math.BigDecimal
import java.time.LocalDateTime

data class RentHistoryDTO(
    val rentHistoryId:String,
    val rentDate: LocalDateTime,
    val returnDate:LocalDateTime,
    val leftDate:Int,
    val status:RentHistoryStatusType,
    val bookId:String,
    val memberId:String
)

data class RentHistoryTotalDTO(
    var content: List<RentHistoryDTO>?,
)