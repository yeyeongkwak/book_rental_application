package com.group.book_application.adapters.r2dbc.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

interface IdentifierGenerator{
    suspend fun generateBookId():String
    suspend fun generateBookHistoryId():String
    suspend fun generatePointId():String
}

//@Repository
//class R2dbcIdentifierGenerator(
//    private val databaseClient: DatabaseClient
//):IdentifierGenerator{
//    override suspend fun generateBookHistoryId(): String {
//        return withContext(Dispatchers.IO){
//            databaseClient.sql("select ")
//        }
//    }
//}