package com.group.book_application.adapters.r2dbc.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

interface IdentifierGenerator{
    suspend fun generateBookId():String
//    suspend fun generateBookHistoryId():String
//    suspend fun generatePointId():String
}

@Repository
class R2dbcIdentifierGenerator(
    private val databaseClient: DatabaseClient
):IdentifierGenerator{
    @Value("\${xc-config.database.sequence.book}")
    lateinit var bookSequenceId: String

    override suspend fun generateBookId(): String {
        return "B"+withContext(Dispatchers.IO) {
            databaseClient.sql("select nextval('$bookSequenceId')")
                .map { t, _ -> t.get("nextval", String::class.java)!! }
                .one().asFlow().single()
        }
    }

//    override suspend fun generateBookHistoryId(): String {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun generatePointId(): String {
//        TODO("Not yet implemented")
//    }

}