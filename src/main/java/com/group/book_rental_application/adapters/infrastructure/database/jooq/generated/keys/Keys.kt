/*
 * This file is generated by jOOQ.
 */
package com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys


import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Book
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Member
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.Point
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables.RentHistory

import org.jooq.ForeignKey
import org.jooq.Record
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val BOOK_PK: UniqueKey<Record> = Internal.createUniqueKey(Book.BOOK, DSL.name("book_pk"), arrayOf(Book.BOOK.BOOK_ID), true)
val MEMBER_PK: UniqueKey<Record> = Internal.createUniqueKey(Member.MEMBER, DSL.name("member_pk"), arrayOf(Member.MEMBER.MEMBER_ID), true)
val RENT_HISTORY_PK: UniqueKey<Record> = Internal.createUniqueKey(RentHistory.RENT_HISTORY, DSL.name("rent_history_pk"), arrayOf(RentHistory.RENT_HISTORY.RENT_HISTORY_ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val POINT__POINT_MEMBER_FK: ForeignKey<Record, Record> = Internal.createForeignKey(Point.POINT, DSL.name("point_member_fk"), arrayOf(Point.POINT.MEMBER_ID), com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys.MEMBER_PK, arrayOf(Member.MEMBER.MEMBER_ID), true)
val RENT_HISTORY__RENT_HISTORY_BOOK_FK: ForeignKey<Record, Record> = Internal.createForeignKey(RentHistory.RENT_HISTORY, DSL.name("rent_history_book_fk"), arrayOf(RentHistory.RENT_HISTORY.BOOK_ID), com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys.BOOK_PK, arrayOf(Book.BOOK.BOOK_ID), true)
val RENT_HISTORY__RENT_HISTORY_MEMBER_FK: ForeignKey<Record, Record> = Internal.createForeignKey(RentHistory.RENT_HISTORY, DSL.name("rent_history_member_fk"), arrayOf(RentHistory.RENT_HISTORY.MEMBER_ID), com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys.MEMBER_PK, arrayOf(Member.MEMBER.MEMBER_ID), true)
