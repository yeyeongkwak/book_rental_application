/*
 * This file is generated by jOOQ.
 */
package com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.tables


import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.BookRental
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys.USER_ID
import com.group.book_rental_application.adapters.infrastructure.database.jooq.generated.keys.USER_PK

import java.time.LocalDateTime

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Member(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    BookRental.BOOK_RENTAL,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>book_rental.member</code>
         */
        val MEMBER: Member = Member()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>book_rental.member.member_id</code>.
     */
    val MEMBER_ID: TableField<Record, String?> = createField(DSL.name("member_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>book_rental.member.member_name</code>.
     */
    val MEMBER_NAME: TableField<Record, String?> = createField(DSL.name("member_name"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>book_rental.member.join_date</code>.
     */
    val JOIN_DATE: TableField<Record, LocalDateTime?> = createField(DSL.name("join_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "")

    /**
     * The column <code>book_rental.member.modify_date</code>.
     */
    val MODIFY_DATE: TableField<Record, LocalDateTime?> = createField(DSL.name("modify_date"), SQLDataType.LOCALDATETIME(6), this, "")

    /**
     * The column <code>book_rental.member.rank</code>.
     */
    val RANK: TableField<Record, String?> = createField(DSL.name("rank"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'BRONZE'::book_rental.member_rank", SQLDataType.VARCHAR)), this, "")

    /**
     * The column <code>book_rental.member.blocked</code>.
     */
    val BLOCKED: TableField<Record, Boolean?> = createField(DSL.name("blocked"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /**
     * The column <code>book_rental.member.total_point</code>.
     */
    val TOTAL_POINT: TableField<Record, Int?> = createField(DSL.name("total_point"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "")

    /**
     * The column <code>book_rental.member.max_rent_count</code>.
     */
    val MAX_RENT_COUNT: TableField<Record, Int?> = createField(DSL.name("max_rent_count"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("5", SQLDataType.INTEGER)), this, "")

    /**
     * The column <code>book_rental.member.current_rent_count</code>.
     */
    val CURRENT_RENT_COUNT: TableField<Record, Int?> = createField(DSL.name("current_rent_count"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>book_rental.member</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>book_rental.member</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>book_rental.member</code> table reference
     */
    constructor(): this(DSL.name("member"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, MEMBER, null)
    override fun getSchema(): Schema? = if (aliased()) null else BookRental.BOOK_RENTAL
    override fun getPrimaryKey(): UniqueKey<Record> = USER_PK
    override fun getUniqueKeys(): List<UniqueKey<Record>> = listOf(USER_ID)
    override fun `as`(alias: String): Member = Member(DSL.name(alias), this)
    override fun `as`(alias: Name): Member = Member(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Member = Member(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Member = Member(name, null)
}
