package com.group.book_application.application.interfaces
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.RentHistory
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.MemberRepository
import com.group.book_application.domain.repository.PointRepository
import com.group.book_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


interface BookRentQueryService {
    suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member>

    //    suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book>
    suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book>

    suspend fun searchPointByMemberId(memberId: String): List<Point>

    suspend fun searchHistories():List<RentHistory>
}

@Service
class BookRentQueryServiceImpl(
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository,
    private val pointRepository: PointRepository,
    private val rentHistoryRepository: RentHistoryRepository
) : BookRentQueryService {

    override suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member> {
        val searchMemberName = memberRepository.searchMembers(memberName, pageable);
        return PageImpl(searchMemberName, pageable, searchMemberName.size.toLong())
    }

//    override suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book> {
//        val books = bookRepository.getAllBooks()
//        val count = books.size
//        return PageImpl(books, condition.pageable!!, count.toLong())
//
//    }

    override suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book> {
        val books = bookRepository.searchBooks(bookName, author, pageable)
        return PageImpl(books, pageable, books.size.toLong())
    }

    override suspend fun searchPointByMemberId(memberId: String): List<Point> {
        return pointRepository.searchPointByMemberId(memberId)
    }

    override suspend fun searchHistories(): List<RentHistory> {
        return rentHistoryRepository.getRentHistories()
    }
}