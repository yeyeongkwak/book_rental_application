package com.group.book_rental_application.domain.repository

import com.group.book_rental_application.application.dto.RentHistoryDTO
import com.group.book_rental_application.application.usecase.condition.BookQueryCondition
import com.group.book_rental_application.application.usecase.condition.RentHistoryQueryCondition
import com.group.book_rental_application.domain.model.Book
import com.group.book_rental_application.domain.model.Member
import com.group.book_rental_application.domain.model.Point
import com.group.book_rental_application.domain.model.RentHistory
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository {
    suspend fun createBook(book: Book)
    suspend fun updateBook(book: Book)

    suspend fun updateBooks(books:List<Book>)
    suspend fun getBookId(bookId: String): Mono<Book>
    suspend fun getAllBooks(): List<Book>
    suspend fun getByBookIds(bookIds:List<String>): Flux<Book>
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

    suspend fun createRentHistories(rentHistories:List<RentHistory>)

    suspend fun getRentHistoryById(historyId: String): Mono<RentHistory>

    suspend fun getRentHistories(): List<RentHistory>
    suspend fun updateRentHistory(rentHistory: RentHistory)
    suspend fun updateRentHistories(rentHistories: List<RentHistory>)
    suspend fun getUserRentHistories(condition: RentHistoryQueryCondition): Flow<RentHistory>
}

interface PointRepository {
    suspend fun createPointHistory(point: Point)
    suspend fun createPointHistories(point: List<Point>)
    suspend fun getPointById(pointId: String): Mono<Point>
    suspend fun searchPointByMemberId(memberId: String): List<Point>
}

interface BookRentUserHistoryRepositoryForJooq {
    suspend fun getRentHistories(condition: RentHistoryQueryCondition): PageImpl<RentHistory>
    suspend fun getRentHistoriesCount(condition: RentHistoryQueryCondition):Int
}


interface BookRepositoryForJooq{
    suspend fun getBooks(condition: BookQueryCondition):PageImpl<Book>
    suspend fun getBooksCount(condition: BookQueryCondition):Int
}