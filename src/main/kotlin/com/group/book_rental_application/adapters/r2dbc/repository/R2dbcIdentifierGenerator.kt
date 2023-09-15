package com.group.book_rental_application.adapters.r2dbc.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

interface IdentifierGenerator{
    suspend fun generateBookId(): String
    suspend fun generateRentHistoryId(): String
    suspend fun generatePointId():String
}

@Repository
class R2dbcIdentifierGenerator(
    private val databaseClient: DatabaseClient
):IdentifierGenerator {

    @Value("\${xc-config.database.sequence.book}")
    lateinit var bookSequenceId: String

    @Value("\${xc-config.database.sequence.rent-history}")
    lateinit var rentHistorySequenceId: String

    @Value("\${xc-config.database.sequence.point}")
    lateinit var pointSequenceId: String

    override suspend fun generateBookId(): String {
        return "B" + withContext(Dispatchers.IO) {
            databaseClient.sql("select nextval('$bookSequenceId')")
                .map { t, _ -> t.get("nextval", String::class.java)!! }
                .one().asFlow().single().toString().padStart(8, '0')
        }
    }

    override suspend fun generateRentHistoryId(): String {
        return "R" + withContext(Dispatchers.IO) {
            databaseClient.sql("select nextval('$rentHistorySequenceId')")
                .map { t, _ -> t.get("nextval", String::class.java)!! }
                .one().asFlow().single().toString().padStart(8, '0')
        }
    }

    override suspend fun generatePointId(): String {
        return "P" + withContext(Dispatchers.IO) {
            databaseClient.sql("select nextval('$pointSequenceId')")
                .map { t, _ -> t.get("nextval", String::class.java)!! }
                .one().asFlow().single().toString().padStart(8, '0')
        }
    }
}