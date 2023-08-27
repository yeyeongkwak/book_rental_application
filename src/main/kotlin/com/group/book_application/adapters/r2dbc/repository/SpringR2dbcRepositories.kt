package com.group.book_application.adapters.r2dbc.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.RentHistory
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//interface CustomBookRepository{
//    fun searchBook(bookName: String, author: String, pageable: Pageable):Flux<Book>
//}

interface SpringDataR2dbcMemberRepository : ReactiveCrudRepository<Member, String> {
    fun findByMemberId(memberId: String): Mono<Member>

    fun findMemberByMemberName(memberName: String, pageable: Pageable): Flux<Member>

}

interface SpringDataR2dbcBookRepository : ReactiveCrudRepository<Book, String>{
    fun findByBookId(bookId: String): Mono<Book>

//    fun findByBookNameAndAuthor(bookName: String, author: String, pageable: Pageable): Flux<Book>
}

interface SpringDataR2dbcPointRepository : ReactiveCrudRepository<Point, String> {
    fun findByPointId(pointId: String): Mono<Point>
}

interface SpringDataR2dbcRentHistory : ReactiveCrudRepository<RentHistory, String> {
    fun findByRentHistoryId(historyId: String): Mono<RentHistory>

}