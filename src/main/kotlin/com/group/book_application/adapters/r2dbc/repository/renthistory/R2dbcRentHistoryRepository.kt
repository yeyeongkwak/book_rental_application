package com.group.book_application.adapters.r2dbc.repository.renthistory

import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcRentHistory
import com.group.book_application.domain.model.RentHistory
import com.group.book_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcRentHistoryRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcRentHistory: SpringDataR2dbcRentHistory
) : RentHistoryRepository {
    override suspend fun createRentHistory(rentHistory: RentHistory) {
        r2dbcTemplate.insert(rentHistory).awaitSingle()
    }

    override suspend fun getRentHistoryById(historyId: String): Mono<RentHistory> {
        return springDataR2dbcRentHistory.findByRentHistoryId(historyId)
    }

    override suspend fun getRentHistories(): List<RentHistory> {
        return springDataR2dbcRentHistory.findAll().asFlow().toList()
    }

    override suspend fun updateRentHistoryById(historyId: String, rentHistory: RentHistory) {
    }
}