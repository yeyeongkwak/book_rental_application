package com.group.book_application.application.interfaces

import com.group.book_application.adapters.interfaces.rest.dto.CreateBookRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateMemberRequest
import com.group.book_application.adapters.r2dbc.repository.R2dbcIdentifierGenerator
import com.group.book_application.domain.enums.BookStatusType
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.PointRepository
import com.group.book_application.domain.repository.RentHistoryRepository
import com.group.book_application.domain.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface BookRentCommandService {
    suspend fun createUser(body: CreateMemberRequest): String
    suspend fun createBook(body: CreateBookRequest): String

}

@Service
class BookRentCommandServiceImpl(
    private val identifierGenerator: R2dbcIdentifierGenerator,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentHistoryRepository: RentHistoryRepository,
    private val pointRepository: PointRepository
) : BookRentCommandService {

    //유저 생성
    @Transactional
    override suspend fun createUser(body: CreateMemberRequest): String {
        val newUser = userRepository.getUserById(body.memberId)
        if (newUser == null) {
            userRepository.createUser(
                Member(
                    memberId = body.memberId,
                    memberName = body.memberName,
                    rank = body.rank,
                    blocked = false,
                    totalPoint = 0
                )
            )
        }
        return body.memberId
    }

    @Transactional
    override suspend fun createBook(body: CreateBookRequest): String {
        val bookId = identifierGenerator.generateBookId()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        if(bookRepository.getBookId(bookId)===null){
        Book(
            bookId = bookId,
            bookName = body.bookName,
            publishDate = LocalDate.parse(body.publishDate, formatter),
            purchaseDate = LocalDate.parse(body.purchaseDate, formatter),
            availableDays = if (body.availableDays != 0) body.availableDays else 14,
            author = body.author,
            status = BookStatusType.AVAILABLE,
            availableRank = body.availableRank
        ).let {
            bookRepository.createBook(it)
        }
//        }
//    println(body)
        println("bookID" + bookId)
        return bookId
    }


}