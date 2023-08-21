package com.group.book_application.domain.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.BookHistory
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.User
import reactor.core.publisher.Mono

interface BookRepository {
    suspend fun createBook(book:Book);
    suspend fun updateBook(book:Book);
    suspend fun getBookId(bookId:String): Mono<Book>

}

interface UserRepository {
    suspend fun createUser(user: User);
    suspend fun updateUser(user:User);
    suspend fun getUserById(userId:String):Mono<User>
}

interface RentHistoryRepository{
    suspend fun createRentHistory(rentHistory:BookHistory)
}

interface PointRepository{
    suspend fun createPointHistory(point: Point)

    suspend fun getPointById(pointId:String):Mono<Point>
}