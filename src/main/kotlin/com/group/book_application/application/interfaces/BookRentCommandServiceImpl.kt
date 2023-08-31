package com.group.book_application.application.interfaces

import com.group.book_application.adapters.interfaces.rest.dto.CreateBookRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateMemberRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateRentHistory
import com.group.book_application.adapters.interfaces.rest.dto.UpdateRent
import com.group.book_application.adapters.r2dbc.repository.R2dbcIdentifierGenerator
import com.group.book_application.domain.enums.AvailableBookType
import com.group.book_application.domain.enums.BookStatusType
import com.group.book_application.domain.enums.PointType
import com.group.book_application.domain.exceptions.BookRentIllegalArgumentException
import com.group.book_application.domain.exceptions.BookRentIllegalStateException
import com.group.book_application.domain.exceptions.BookRentNotFoundIllegalException
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.model.Point
import com.group.book_application.domain.model.RentHistory
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.MemberRepository
import com.group.book_application.domain.repository.PointRepository
import com.group.book_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.transaction.Transactional
import kotlin.math.abs

interface BookRentCommandService {
    suspend fun createUser(body: CreateMemberRequest): String
    suspend fun getMemberById(memberId: String): Member?

    suspend fun createBook(body: CreateBookRequest): String
    suspend fun getBookByBookId(bookId: String): Book?
    suspend fun createRentHistories(memberId: String, body: List<CreateRentHistory>)
    suspend fun updateRentHistory(body: List<UpdateRent>)
    suspend fun updateRentHistories(memberId: String, body: List<UpdateRent>)

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
        return bookId
    }

    override suspend fun getBookByBookId(bookId: String): Book? {
        return bookRepository.getBookId(bookId).awaitSingle()
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

    override suspend fun createRentHistories(memberId: String, body: List<CreateRentHistory>) {
        val member = getMemberById(memberId) ?: throw BookRentNotFoundIllegalException("$memberId does not found")
        val updateBooks = ArrayList<Book>(body.size);
        val newPoints = ArrayList<Point>(body.size);
        val newRentHistories = ArrayList<RentHistory>(body.size);
        when {
            //회원 자격이 정지됐다면 에러
            member.blocked -> throw BookRentIllegalArgumentException("${member.memberId} is blocked.")
            //회원이 빌릴 수 있는 대여권수를 초과할때 에러 메시지
            member.maxRentCount < member.currentRentCount + body.size -> throw BookRentIllegalStateException("Available rent count is ${member.maxRentCount}. You tried to borrow ${member.currentRentCount + body.size} in total")
            else -> {
                //대여가능한 케이스
                body.map { rb ->
                    val book = getBookByBookId(bookId = rb.bookId)
                        ?: throw BookRentNotFoundIllegalException("${rb.bookId} does not found")
                    when {
                        //이미 빌리려는 책이 대여상태이면..! 에러 메시지
                        book.status == BookStatusType.RENT -> throw BookRentIllegalArgumentException("${book.bookName} is already rent")
                        //회원이 빌릴 수 있는 책과 맞지 않는 경우 에러 메시지
                        book.availableRank != AvailableBookType.ALL && book.availableRank.toString() != member.rank.toString() -> throw BookRentIllegalArgumentException(
                            "This book can be rent by the rank from ${book.availableRank}"
                        )

                        else -> {
                            //대여기록 ID 생성
                            val rentHistoryId = identifierGenerator.generateRentHistoryId();
                            newRentHistories.add(
                                RentHistory(
                                    rentHistoryId = rentHistoryId, memberId = member.memberId, bookId = book.bookId
                                )
                            )
                            rentHistoryRepository.createRentHistory(
                                RentHistory(
                                    rentHistoryId = rentHistoryId, memberId = member.memberId, bookId = book.bookId
                                )
                            ).apply { //apply=>객체 생성과 동시에 초기화를 할 때 사용
                                book.rent()
                                bookRepository.updateBook(book)
                            }.apply {
                                //포인트 아이디 생성
                                val pointId = identifierGenerator.generatePointId()
                                newPoints.add(
                                    Point(
                                        pointId = pointId,
                                        totalAmount = 200,
                                        memberId = member.memberId,
                                        type = PointType.GAIN
                                    )
                                )
                                pointRepository.createPointHistory(
                                    Point(
                                        pointId = pointId,
                                        totalAmount = 200,
                                        memberId = member.memberId,
                                        type = PointType.GAIN
                                    )
                                )
                            }

                        }
                    }
                }.apply {
                    val totalPointSumByMember =
                        pointRepository.searchPointByMemberId(member.memberId).sumOf { p -> p.totalAmount };
                    member.updatePoint(totalPointSumByMember)
                    memberRepository.updateUser(member)
                }.apply {
                    member.updateRank();
                    member.updateMaxRentCount()
                    member.updateCurrentRentCount(body.size)
                    memberRepository.updateUser(member)
                }
            }
        }
    }

    override suspend fun updateRentHistory(body: List<UpdateRent>) {
        body.map { b ->
            val existRentHistory = rentHistoryRepository.getRentHistoryById(b.rentHistoryId).awaitSingle()
            val pointId = identifierGenerator.generatePointId()

            existRentHistory.changeStatus(existRentHistory.leftDate)
            rentHistoryRepository.updateRentHistory(existRentHistory).apply {
                val book = getBookByBookId(bookId = existRentHistory.bookId)
                    ?: throw BookRentNotFoundIllegalException("${b.rentHistoryId} does not found")
                if (existRentHistory.leftDate >= 0) {
                    book.returnBooks()
                    bookRepository.updateBook(book)
                } else {
                    book.delayReturnBooks().apply {
                        pointRepository.createPointHistory(
                            Point(
                                pointId = pointId,
                                totalAmount = 200,
                                memberId = existRentHistory.memberId,
                                type = PointType.GAIN
                            )
                        )
                    }
                    //포인트 차감~
                    //회원 강등~
                }
            }
        }
    }

    override suspend fun updateRentHistories(memberId: String, body: List<UpdateRent>) {
        val member = getMemberById(memberId) ?: throw BookRentNotFoundIllegalException("$memberId does not found")
        when (member) {
            else -> {
                val updateRentHistories = ArrayList<RentHistory>(body.size)
                val newPoints = ArrayList<Point>(body.size)
                val updateBooks = ArrayList<Book>(body.size)

                body.map { b ->
                    val existRentHistory = rentHistoryRepository.getRentHistoryById(b.rentHistoryId).awaitSingle()
                    val book = getBookByBookId(bookId = existRentHistory.bookId)
                        ?: throw BookRentNotFoundIllegalException("${b.rentHistoryId} does not found")
                    val pointId = identifierGenerator.generatePointId()

                    existRentHistory.changeStatus(existRentHistory.leftDate)
                    updateRentHistories.add(existRentHistory)
                    when {
                        existRentHistory.leftDate >= 0 -> {
                            book.returnBooks()
                            updateBooks.add(book)
                        }

                        else -> book.delayReturnBooks().apply {
                            newPoints.add(
                                Point(
                                    pointId = pointId,
                                    totalAmount = -(300 * abs(existRentHistory.leftDate)),
                                    memberId = existRentHistory.memberId,
                                    type = PointType.GAIN
                                )
                            )
                        }
                    }
                    rentHistoryRepository.updateRentHistories(updateRentHistories)
                        .apply {
                            pointRepository.createPointHistories(newPoints)
                        }
                        .apply {
                            member.updateRank();
                            member.updateMaxRentCount()
                            member.updateCurrentRentCount(body.size)
                            memberRepository.updateUser(member)
                        }
                }
            }
        }
    }
}

