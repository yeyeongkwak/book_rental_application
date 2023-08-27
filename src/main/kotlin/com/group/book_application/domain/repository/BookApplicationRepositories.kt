package com.group.book_application.domain.repository

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.RentHistory
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository {
    suspend fun createBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun getBookId(bookId: String): Mono<Book>
    suspend fun getAllBooks(): List<Book>
//    suspend fun getBooksByNameAndAuthor(bookName: String, author:String, pageable: Pageable):List<Book>
    suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): List<Book>
}

interface MemberRepository {
    suspend fun createUser(member: Member)
    suspend fun updateUser(member: Member)
    suspend fun getMemberByMemberId(memberId: String): Mono<Member>
    suspend fun getMemberByName(memberName: String, pageable: Pageable): List<Member>

    //    suspend fun searchMembers(memberName: String,pageable: Pageable):List<Member>
    suspend fun searchMembers(memberName: String, pageable: Pageable):List<Member>

}

interface RentHistoryRepository {
    suspend fun createRentHistory(rentHistory: RentHistory)
    suspend fun getRentHistoryById(historyId: String): Mono<RentHistory>

    suspend fun getRentHistories(): List<RentHistory>
    suspend fun updateRentHistoryById(historyId: String, rentHistory: RentHistory)
}

interface PointRepository {
    suspend fun createPointHistory(point: Point)
    suspend fun getPointById(pointId: String): Mono<Point>
    suspend fun searchPointByMemberId(memberId: String): List<Point>
}