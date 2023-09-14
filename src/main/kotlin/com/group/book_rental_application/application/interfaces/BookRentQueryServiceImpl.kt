package com.group.book_rental_application.application.interfaces

import com.group.book_rental_application.application.usecase.condition.BookQueryCondition
import com.group.book_rental_application.application.usecase.condition.RentHistoryQueryCondition
import com.group.book_rental_application.domain.model.Book
import com.group.book_rental_application.domain.model.Member
import com.group.book_rental_application.domain.model.Point
import com.group.book_rental_application.domain.model.RentHistory
import com.group.book_rental_application.domain.repository.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


interface BookRentQueryService {
    suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member>
    suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book>
    suspend fun searchPointByMemberId(memberId: String): List<Point>
    suspend fun searchHistories(pageable: Pageable): PageImpl<RentHistory>
    suspend fun getUserRentHistories(condition: RentHistoryQueryCondition): PageImpl<RentHistory>
    suspend fun getBooks(condition: BookQueryCondition): PageImpl<Book>
}

@Service
class BookRentQueryServiceImpl(
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository,
    private val pointRepository: PointRepository,
    private val rentHistoryRepository: RentHistoryRepository,
    private val rentUserHistoryRepositoryForJooq: BookRentUserHistoryRepositoryForJooq,
    private val bookRepositoryForJooq: BookRepositoryForJooq
) : BookRentQueryService {

    override suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member> {
        val searchMemberName = memberRepository.searchMembers(memberName, pageable);
        return PageImpl(searchMemberName, pageable, searchMemberName.size.toLong())
    }

    override suspend fun getBooks(condition: BookQueryCondition): PageImpl<Book> {
        return bookRepositoryForJooq.getBooks(condition)
    }

    override suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book> {
        val books = bookRepository.searchBooks(bookName, author, pageable)
        return PageImpl(books, pageable, books.size.toLong())
    }

    override suspend fun searchPointByMemberId(memberId: String): List<Point> {
        return pointRepository.searchPointByMemberId(memberId)
    }

    override suspend fun searchHistories(pageable: Pageable): PageImpl<RentHistory> {
        val rentHistories = rentHistoryRepository.getRentHistories()
        return PageImpl(rentHistories, pageable, rentHistories.size.toLong())

    }
    override suspend fun getUserRentHistories(condition: RentHistoryQueryCondition): PageImpl<RentHistory> {
        return rentUserHistoryRepositoryForJooq.getRentHistories(condition)
    }

}