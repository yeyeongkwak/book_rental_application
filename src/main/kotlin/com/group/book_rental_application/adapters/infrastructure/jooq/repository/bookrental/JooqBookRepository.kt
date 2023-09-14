package com.group.book_rental_application.adapters.infrastructure.jooq.repository.bookrental

import com.group.book_rental_application.adapters.infrastructure.jooq.DslContextWrapper
import com.group.book_rental_application.adapters.infrastructure.jooq.mapper.BookDTOMapper
import com.group.book_rental_application.adapters.interfaces.jooq.JooqSQL
import com.group.book_rental_application.application.usecase.condition.BookQueryCondition
import com.group.book_rental_application.domain.model.Book
import com.group.book_rental_application.domain.repository.BookRepositoryForJooq
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository


@Repository
data class JooqBookRepository(
    private val dslContextWrapper: DslContextWrapper

) : BookRepositoryForJooq {
    val log: Logger = LoggerFactory.getLogger(JooqBookRentUserHistoryRepository::class.java)

    override suspend fun getBooks(condition: BookQueryCondition): PageImpl<Book> {
        val whereCondition = JooqSQL.bookWhereCondition(condition)
        val books = dslContextWrapper.withDSLContextMany {
            JooqSQL.bookSelect(it, condition, whereCondition)
        }.bufferUntilChanged {
            it[JooqSQL.bk.BOOK_ID]!!
        }.map {
            BookDTOMapper.into(it)
        }.asFlow().toList()
        val count = getBooksCount(condition).toLong()
        return PageImpl(books, condition.pageable!!, count)
    }

    override suspend fun getBooksCount(condition: BookQueryCondition): Int {
        val whereCondition = JooqSQL.bookWhereCondition(condition)
        return dslContextWrapper.withDSLContextMany {
            JooqSQL.booksCount(it, whereCondition)
        }.map {
            it.into(Int::class.java)
        }.awaitSingle()
    }
}
