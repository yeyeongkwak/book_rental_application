package com.group.book_rental_application.adapters.infrastructure.jooq.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.book_rental_application.adapters.interfaces.jooq.JooqSQL
import com.group.book_rental_application.domain.enums.BookStatusType
import com.group.book_rental_application.domain.model.Book
import org.jooq.Record

object BookDTOMapper {
    val mapper = ObjectMapper()
    fun into(bk:List<Record>): Book {
        return Book(
            bookId = bk[0][JooqSQL.bk.BOOK_ID]!!,
            bookName = bk[0][JooqSQL.bk.BOOK_NAME]!!,
            author = bk[0][JooqSQL.bk.AUTHOR]!!,
            status = BookStatusType.valueOf(bk[0][JooqSQL.bk.STATUS]!!),
            publishDate = bk[0][JooqSQL.bk.PUBLISH_DATE]!!,
            purchaseDate = bk[0][JooqSQL.bk.PURCHASE_DATE]!!
        )
    }
}