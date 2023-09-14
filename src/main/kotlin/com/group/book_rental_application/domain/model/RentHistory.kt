package com.group.book_rental_application.domain.model

import com.group.book_rental_application.domain.enums.RentHistoryStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "rent_history")
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
    fun changeStatus(days: Int) {
        status = when {
            days < 0 -> {
                RentHistoryStatusType.DELAYED
            }

            else -> status // RENT? RETURN?
        }
    }

}