package com.group.book_rental_application.adapters.infrastructure.jooq.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.book_rental_application.adapters.interfaces.jooq.JooqSQL
import com.group.book_rental_application.domain.enums.RentHistoryStatusType
import com.group.book_rental_application.domain.model.RentHistory
import org.jooq.Record

object RentHistoryDTOMapper {
    val mapper = ObjectMapper()

    fun into(rh: List<Record>): RentHistory {
        return RentHistory(
            rentHistoryId = rh[0][JooqSQL.rh.RENT_HISTORY_ID]!!,
            rentDate = rh[0][JooqSQL.rh.RENT_DATE]!!,
            bookId = rh[0][JooqSQL.rh.BOOK_ID]!!,
            returnDate = rh[0][JooqSQL.rh.RETURN_DATE]!!,
            memberId = rh[0][JooqSQL.rh.MEMBER_ID]!!,
            leftDate = rh[0][JooqSQL.rh.LEFT_DATE]!!,
            status = RentHistoryStatusType.valueOf(rh[0][JooqSQL.rh.STATUS]!!),
        )
    }
}