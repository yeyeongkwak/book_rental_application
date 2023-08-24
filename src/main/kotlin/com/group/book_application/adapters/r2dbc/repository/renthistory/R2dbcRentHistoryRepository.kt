package com.group.book_application.adapters.r2dbc.repository.renthistory

import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcRentHistory
import com.group.book_application.domain.model.BookHistory
import com.group.book_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcRentHistoryRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcRentHistory: SpringDataR2dbcRentHistory
) : RentHistoryRepository {
    override suspend fun createRentHistory(rentHistory: BookHistory) {
        r2dbcTemplate.insert(rentHistory).awaitSingle()
    }

    override suspend fun getBookRentHistoryById(historyId: String): Mono<BookHistory> {
        return springDataR2dbcRentHistory.findByBookHistoryId(historyId)
    }
}