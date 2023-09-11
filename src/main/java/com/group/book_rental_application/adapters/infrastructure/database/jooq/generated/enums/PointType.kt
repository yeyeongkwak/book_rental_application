/*
 * This file is generated by jOOQ.
 */
package com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.enums


import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.BookRental

import org.jooq.Catalog
import org.jooq.EnumType
import org.jooq.Schema


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
enum class PointType(@get:JvmName("literal") public val literal: String) : EnumType {
    CHARGE("CHARGE"),
    GAIN("GAIN"),
    SUB("SUB");
    override fun getCatalog(): Catalog? = schema.catalog
    override fun getSchema(): Schema = BookRental.BOOK_RENTAL
    override fun getName(): String = "point_type"
    override fun getLiteral(): String = literal
}
