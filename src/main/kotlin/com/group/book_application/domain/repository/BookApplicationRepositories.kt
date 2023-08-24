package com.group.book_application.domain.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.BookHistory
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import reactor.core.publisher.Mono

interface BookRepository {
    suspend fun createBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun getBookId(bookId: String): Mono<Book>

    //    suspend fun getBooks(bookQueryCondition: BookQueryCondition): Flux<Book>
    suspend fun getAllBooks(): List<Book>
}

interface UserRepository {
    suspend fun createUser(user: Member);
    suspend fun updateUser(user: Member)
    suspend fun getUserById(userId: String): Member?
}

interface RentHistoryRepository {
    suspend fun createRentHistory(rentHistory: BookHistory)
    suspend fun getBookRentHistoryById(historyId: String): Mono<BookHistory>
}

interface PointRepository {
    suspend fun createPointHistory(point: Point)

    suspend fun getPointById(pointId: String): Mono<Point>
}