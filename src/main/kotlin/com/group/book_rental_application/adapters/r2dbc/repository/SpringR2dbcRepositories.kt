package com.group.book_rental_application.adapters.r2dbc.repository

import com.group.book_rental_application.domain.model.Book
import com.group.book_rental_application.domain.model.Member
import com.group.book_rental_application.domain.model.Point
import com.group.book_rental_application.domain.model.RentHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface SpringDataR2dbcMemberRepository : ReactiveCrudRepository<Member, String> {
    fun findByMemberId(memberId: String): Mono<Member>

    fun findMemberByMemberName(memberName: String, pageable: Pageable): Flux<Member>

}

interface SpringDataR2dbcBookRepository : ReactiveCrudRepository<Book, String>{
    fun findByBookId(bookId: String): Mono<Book>

    fun findByBookIdIsIn(bookNo: List<String>):Flux<Book>

}

interface SpringDataR2dbcPointRepository : ReactiveCrudRepository<Point, String> {
    fun findByPointId(pointId: String): Mono<Point>
    fun findAllByMemberId(memberId: String): Flux<Point>
}

interface SpringDataR2dbcRentHistory : ReactiveCrudRepository<RentHistory, String> {
    fun findByRentHistoryId(historyId: String): Mono<RentHistory>
}

interface SpringDataR2dbcUserRentHistory: ReactiveCrudRepository<RentHistory, String>{
    fun findByMemberId(memberId: String):Flux<RentHistory>
}