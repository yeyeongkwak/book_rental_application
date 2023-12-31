package com.group.book_rental_application.adapters.r2dbc.repository.renthistory

import com.group.book_rental_application.adapters.r2dbc.repository.SpringDataR2dbcRentHistory
import com.group.book_rental_application.adapters.r2dbc.repository.SpringDataR2dbcUserRentHistory
import com.group.book_rental_application.application.usecase.condition.RentHistoryQueryCondition
import com.group.book_rental_application.domain.model.RentHistory
import com.group.book_rental_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcRentHistoryRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcRentHistory: SpringDataR2dbcRentHistory,
    val springDataR2dbcUserRentHistory: SpringDataR2dbcUserRentHistory
) : RentHistoryRepository {
    override suspend fun createRentHistory(rentHistory: RentHistory) {
        r2dbcTemplate.insert(rentHistory).awaitSingle()
    }

    override suspend fun createRentHistories(rentHistories: List<RentHistory>) {
        springDataR2dbcRentHistory.saveAll(rentHistories).asFlow().toList()
    }

    override suspend fun getRentHistoryById(historyId: String): Mono<RentHistory> {
        return springDataR2dbcRentHistory.findByRentHistoryId(historyId)
    }

    override suspend fun getRentHistories(): List<RentHistory> {
        return springDataR2dbcRentHistory.findAll().asFlow().toList()
    }


    override suspend fun getUserRentHistories(condition: RentHistoryQueryCondition): Flow<RentHistory>{
        return springDataR2dbcUserRentHistory.findByMemberId(condition.memberId).asFlow()
    }

    override suspend fun updateRentHistory(rentHistory: RentHistory) {
        springDataR2dbcRentHistory.save(rentHistory).awaitSingle()
    }

    override suspend fun updateRentHistories(rentHistories: List<RentHistory>) {
        springDataR2dbcRentHistory.saveAll(rentHistories).awaitSingle()
    }


}