package com.group.book_rental_application.domain.model

import com.group.book_rental_application.domain.enums.RentHistoryStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "book_rental.rent_history")
data class RentHistory(
    @Id
    val rentHistoryId: String,
    val rentDate: LocalDateTime = LocalDateTime.now(),
    val returnDate: LocalDateTime = LocalDateTime.now().plusDays(14),
    var leftDate: Int = 14,
    var status: RentHistoryStatusType = RentHistoryStatusType.RENT,
    val bookId: String,
    val memberId: String,
) {
    fun changeStatus(date: Int) {
        status = when {
            leftDate < 0 -> {
                RentHistoryStatusType.DELAYED
            }
            else -> RentHistoryStatusType.RENT // RENT? RETURN?
        }
    }

}