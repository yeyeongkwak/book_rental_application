package com.group.book_rental_application.adapters.interfaces.jooq

import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.references.BOOK
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.references.MEMBER
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.references.RENT_HISTORY
import com.group.book_rental_application.application.usecase.condition.BookQueryCondition
import com.group.book_rental_application.application.usecase.condition.MemberQueryCondition
import com.group.book_rental_application.application.usecase.condition.RentHistoryQueryCondition
import org.jooq.*
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.toChar

object JooqSQL {
    val mb = MEMBER.`as`("mb")
    val bk = BOOK.`as`("bk")
    val rh = RENT_HISTORY.`as`("rh")

    fun rentUserHistoryWhereCondition(queryCondition: RentHistoryQueryCondition): Condition {
        var condition = noCondition()
        if (queryCondition.memberId.isNotBlank()) {
            condition = condition.and(rh.MEMBER_ID.eq(queryCondition.memberId))
        }
        return condition
    }

    fun bookWhereCondition(queryCondition: BookQueryCondition): Condition {
        var condition = noCondition()
        if (queryCondition.bookName.isNotBlank()) {
            condition = condition.and(bk.BOOK_NAME.like("%${queryCondition.bookName}%"))
        }
        if (queryCondition.author.isNotBlank()) {
            condition = condition.and(bk.AUTHOR.like("%${queryCondition.author}%"))
        }
        return condition
    }

    fun memberWhereCondition(queryCondition: MemberQueryCondition): Condition {
        var condition = noCondition()
        if (queryCondition.memberName.isNotBlank()) {
            condition = condition.and(mb.MEMBER_NAME.like("%${queryCondition.memberName}%"))
        }
        return condition
    }
    fun rentUserHistorySelect(
        dslContext: DSLContext,
        queryCondition: RentHistoryQueryCondition, condition: Condition
    ): SelectLimitPercentAfterOffsetStep<Record> {
        val fields = rentUserHistoryFields()
        return dslContext
            .select(fields)
            .from(rh)
            .where(condition)
            .orderBy(rh.RENT_DATE.desc())
            .offset(queryCondition.pageable?.offset)
            .limit(queryCondition.pageable?.pageSize)
    }

    fun bookSelect(dslContext: DSLContext, queryCondition: BookQueryCondition, condition: Condition)
    :SelectLimitPercentAfterOffsetStep<Record>{
        val fields = booksFields()
        return dslContext
            .select(fields)
            .from(bk)
            .where(condition)
            .orderBy(bk.BOOK_ID.desc())
            .offset(queryCondition.pageable?.offset)
            .limit(queryCondition.pageable?.pageSize)
    }

    fun userRentHistoriesCount(dslContext: DSLContext, condition: Condition):SelectConditionStep<Record1<Int>>{
        return dslContext
            .selectCount()
            .from(rh)
            .where(condition)
    }

    fun booksCount(dslContext: DSLContext, condition: Condition):SelectConditionStep<Record1<Int>>{
        return dslContext
            .selectCount()
            .from(bk)
            .where(condition)
    }


    private fun rentUserHistoryFields(): List<SelectFieldOrAsterisk> {
        return listOf(
            rh.RENT_HISTORY_ID,
            rh.BOOK_ID,
            rh.RENT_DATE,
            rh.LEFT_DATE,
            rh.RETURN_DATE,
            rh.MEMBER_ID,
            rh.STATUS
        )
    }

    private fun booksFields():List<SelectFieldOrAsterisk>{
        return listOf(
            bk.BOOK_ID,
            bk.BOOK_NAME,
            bk.AUTHOR,
            bk.STATUS,
            bk.PURCHASE_DATE,
            bk.PUBLISH_DATE
        )
    }
}