package com.group.book_rental_application.adapters.infrastructure.jooq.repository.bookrental

import com.group.book_rental_application.adapters.infrastructure.jooq.DslContextWrapper
import com.group.book_rental_application.adapters.infrastructure.jooq.mapper.RentHistoryDTOMapper
import com.group.book_rental_application.adapters.interfaces.jooq.JooqSQL
import com.group.book_rental_application.application.usecase.condition.RentHistoryQueryCondition
import com.group.book_rental_application.domain.model.RentHistory
import com.group.book_rental_application.domain.repository.BookRentUserHistoryRepositoryForJooq
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository

@Repository
data class JooqBookRentUserHistoryRepository(
    private val dslContextWrapper: DslContextWrapper
) : BookRentUserHistoryRepositoryForJooq {
    val log: Logger = LoggerFactory.getLogger(JooqBookRentUserHistoryRepository::class.java)

    override suspend fun getRentHistories(condition: RentHistoryQueryCondition): PageImpl<RentHistory> {
        val whereCondition = JooqSQL.rentUserHistoryWhereCondition(condition)
        val rentHistories = dslContextWrapper.withDSLContextMany {
            JooqSQL.rentUserHistorySelect(it, condition, whereCondition)
        }.bufferUntilChanged {
            it[JooqSQL.rh.RENT_HISTORY_ID]!!
        }.map {
            RentHistoryDTOMapper.into(
                it
            )
        }.asFlow().toList()
        val count = getRentHistoriesCount(condition).toLong()
        return PageImpl(rentHistories, condition.pageable!!, count)
    }

    override suspend fun getRentHistoriesCount(condition: RentHistoryQueryCondition): Int {
        val whereCondition = JooqSQL.rentUserHistoryWhereCondition(condition)
        return dslContextWrapper.withDSLContextMany {
            JooqSQL.userRentHistoriesCount(it, whereCondition)
        }.map { it.into(Int::class.java) }.awaitSingle()
    }

}
