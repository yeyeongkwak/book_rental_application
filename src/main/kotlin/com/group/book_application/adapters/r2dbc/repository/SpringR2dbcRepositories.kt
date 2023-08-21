package com.group.book_application.adapters.r2dbc.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.BookHistory
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface SpringDataR2dbcUserRepository:ReactiveCrudRepository<User,String>{
    fun findByUserId(userId:String): Mono<User>
}

interface SpringDataR2dbcBookRepository:ReactiveCrudRepository<Book,String>{
    fun findByBookId(bookId:String):Mono<Book>
}

interface SpringDataR2dbcPointRepository:ReactiveCrudRepository<Point,String>{
    fun findByPointId(pointId:String):Mono<Point>
}

interface SpringDataR2dbcRentHistory:ReactiveCrudRepository<BookHistory,String>{
    fun findBookRentHistory(historyId:String):Mono<BookHistory>
}