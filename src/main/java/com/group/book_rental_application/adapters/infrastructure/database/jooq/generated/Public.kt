/*
 * This file is generated by jOOQ.
 */
package com.group.book_rental_application.adapters.infrastructure.database.jooq.generated


import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Book
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Member
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Point
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.RentHistory

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC: Public = Public()
    }

    /**
     * The table <code>public.book</code>.
     */
    val BOOK: Book get() = Book.BOOK

    /**
     * The table <code>public.member</code>.
     */
    val MEMBER: Member get() = Member.MEMBER

    /**
     * The table <code>public.point</code>.
     */
    val POINT: Point get() = Point.POINT

    /**
     * The table <code>public.rent_history</code>.
     */
    val RENT_HISTORY: RentHistory get() = RentHistory.RENT_HISTORY

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Book.BOOK,
        Member.MEMBER,
        Point.POINT,
        RentHistory.RENT_HISTORY
    )
}
