package com.group.book_application.application.interfaces

import com.group.book_application.adapters.interfaces.rest.dto.CreateBookRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateMemberRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateRentHistory
import com.group.book_application.adapters.r2dbc.repository.R2dbcIdentifierGenerator
import com.group.book_application.domain.enums.BookStatusType
import com.group.book_application.domain.enums.PointType
import com.group.book_application.domain.exceptions.BookRentIllegalArgumentException
import com.group.book_application.domain.exceptions.BookRentNotFoundIllegalException
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.RentHistory
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.MemberRepository
import com.group.book_application.domain.repository.PointRepository
import com.group.book_application.domain.repository.RentHistoryRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface BookRentCommandService {
    suspend fun createUser(body: CreateMemberRequest): String
    suspend fun getMemberById(memberId: String): Member?

    suspend fun createBook(body: CreateBookRequest): String
    suspend fun getBookByBookId(bookId: String): Book?
    suspend fun createRentHistory(body: CreateRentHistory): String
//    suspend fun updateRentHistory(rentHistory: RentHistory):RentHistory

}

@Service
class BookRentCommandServiceImpl(
    private val identifierGenerator: R2dbcIdentifierGenerator,
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository,
    private val rentHistoryRepository: RentHistoryRepository,
    private val pointRepository: PointRepository
) : BookRentCommandService {

    //유저 생성
    @Transactional
    override suspend fun createUser(body: CreateMemberRequest): String {
        val newUser = memberRepository.getMemberByMemberId(body.memberId)
        if (newUser == null) {
            memberRepository.createUser(
                Member(
                    memberId = body.memberId,
                    memberName = body.memberName,
                )
            )
        }
        return body.memberId
    }

    @Transactional
    override suspend fun createBook(body: CreateBookRequest): String {
        val bookId = identifierGenerator.generateBookId();
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Book(
            bookId = bookId,
            bookName = body.bookName,
            publishDate = LocalDate.parse(body.publishDate, formatter),
            purchaseDate = LocalDate.parse(body.purchaseDate, formatter),
            author = body.author,
            availableRank = body.availableRank
        ).let {
            bookRepository.createBook(it)
        }

        println("bookID" + bookId)
        return bookId
    }

    override suspend fun getBookByBookId(bookId: String): Book? {
        return bookRepository.getBookId(bookId).awaitSingleOrNull()
    }

    override suspend fun getMemberById(memberId: String): Member? {
        return memberRepository.getMemberByMemberId(memberId).awaitSingleOrNull()
    }


    /*
    책을 대여한다는 것
    1. 책 대여 히스토리 테이블에 데이터를 생성해준다
    2. 책 테이블의 상태를 바꿔준다(AVAILABLE=>RENT)
    3. 포인트 이력 테이블에 데이터를 생성해준다
    4. 포인트 이력 테이블에서 유저의 포인트 이력을 찾아 최종 포인트 합을 더해준 후 유저 정보를 업데이트 한다.
    */

    @Transactional
    override suspend fun createRentHistory(body: CreateRentHistory): String {
        val book = getBookByBookId(bookId = body.bookId)
            ?: throw BookRentNotFoundIllegalException("${body.bookId} does not found")
        val member =
            getMemberById(body.memberId) ?: throw BookRentNotFoundIllegalException("${body.memberId} does not found")

        val rentHistoryId = identifierGenerator.generateRentHistoryId();
        val rentHistory = RentHistory(
            rentHistoryId = rentHistoryId,
            memberId = member.memberId,
            bookId = book.bookId
        )
        val pointId = identifierGenerator.generatePointId()
        val existRentHistory = bookRepository.getBookId(book.bookId).awaitSingleOrNull()!!.status

        //이미 빌리려는 책이 대여상태이면..! 에러 메시지
        if (existRentHistory == BookStatusType.RENT) {
            throw BookRentIllegalArgumentException("${book.bookName} is already rent")
        } else {
            rentHistoryRepository.createRentHistory(rentHistory)
                .apply { //apply=>객체 생성과 동시에 초기화를 할 때 사용
                    book.rentBooks(book)
                    bookRepository.updateBook(book)
                }
                .apply {
                    pointRepository.createPointHistory(
                        Point(
                            pointId = pointId,
                            totalAmount = 200,
                            memberId = member.memberId,
                            type = PointType.GAIN
                        )
                    )
                }
                .apply {
                    val totalPointSumByMember =
                        pointRepository.searchPointByMemberId(member.memberId).sumOf { p -> p.totalAmount };
                    member.updatePoint(totalPointSumByMember)
                    memberRepository.updateUser(member)
                }.apply {
                    member.updateRank(member);
                    memberRepository.updateUser(member)
                }
        }
        return rentHistoryId
    }

//    override suspend fun updateRentHistory(rentHistory: RentHistory): RentHistory {
//
//    }

}


