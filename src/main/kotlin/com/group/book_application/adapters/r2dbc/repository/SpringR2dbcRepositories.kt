package com.group.book_application.adapters.r2dbc.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.BookHistory
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface SpringDataR2dbcUserRepository : ReactiveCrudRepository<Member, String> {
    fun findByMemberId(memberId: String): Mono<Member>

}

interface SpringDataR2dbcBookRepository : ReactiveCrudRepository<Book, String> {
    fun findByBookId(bookId: String): Mono<Book>

//    fun findByBookNameAndAuthor(query: BookQueryCondition): Flux<Book>
}

interface SpringDataR2dbcPointRepository : ReactiveCrudRepository<Point, String> {
    fun findByPointId(pointId: String): Mono<Point>
}

interface SpringDataR2dbcRentHistory : ReactiveCrudRepository<BookHistory, String> {
    fun findByBookHistoryId(historyId: String): Mono<BookHistory>
}