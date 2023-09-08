package com.group.book_rental_application.adapters.infrastructure.jooq

import io.r2dbc.spi.Connection
import org.jooq.DSLContext
import org.jooq.Publisher
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Repository
class DslContextWrapper(
    private val databaseClient: DatabaseClient
) {
    private val settings = Settings()
        .withBindOffsetDateTimeType(true)
        .withBindOffsetTimeType(true)

    private fun Connection.dsl() =
        DSL.using(this, SQLDialect.POSTGRES, settings)

    fun <T : Any> withDSLContextMany(block: (DSLContext) -> Publisher<T>): Flux<T> =
        databaseClient.inConnectionMany { con -> block(con.dsl()).toFlux() }

    fun <T : Any> withDSLContext(block: (DSLContext) -> Publisher<T>): Mono<T> =
        databaseClient.inConnection { con -> block(con.dsl()).toMono() }
}
