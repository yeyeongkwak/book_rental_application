package com.group.book_application.application.usecase.command.handler

import com.group.book_application.adapters.r2dbc.repository.R2dbcIdentifierGenerator
import com.group.book_application.domain.enums.BookStatusType
import com.group.book_application.domain.enums.RentHistoryStatusType
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.MemberRepository
import com.group.book_application.domain.repository.PointRepository
import com.group.book_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period


interface CalculateLeftDateExecutor {
    fun subRentDate()

//    fun subPoint(memberId: String)
}

@Service
class CalculateLeftDateExecutorImpl(
    private val rentHistoryRepository: RentHistoryRepository,
    private val pointRepository: PointRepository,
    private val bookRepository: BookRepository,
    private val memberRepository: MemberRepository,
    private val identifierGenerator: R2dbcIdentifierGenerator
) :
    CalculateLeftDateExecutor {
    override fun subRentDate() {

        runBlocking {
            launch {
                val rentHistories = rentHistoryRepository.getRentHistories()
                //반납된 애들이 아닌 애들만
                rentHistories.map { m ->
                    val rentHistory = rentHistoryRepository.getRentHistoryById(m.rentHistoryId).awaitSingle()
                    val newLeftDate = Period.between(LocalDate.now(), m.returnDate.toLocalDate()).days
                    val updatedRentHistory = rentHistory.copy(leftDate = newLeftDate)

                    if (m.status == RentHistoryStatusType.RETURNED) {
                        rentHistoryRepository.updateRentHistory(rentHistory.copy(leftDate = 0))
                    } else if (updatedRentHistory.leftDate < 0) {
                        //대여 이력 상태=> DELAYED(연체)로 변경
                        val updatedRentHistoryStatus = rentHistory.copy(status = RentHistoryStatusType.DELAYED)
                        rentHistoryRepository.updateRentHistory(updatedRentHistoryStatus)

                        //책 상태 => DELAYED(연체)로 변경
                        val rentBook = bookRepository.getBookId(m.rentHistoryId).awaitSingle()
                        bookRepository.updateBook(rentBook.copy(status = BookStatusType.DELAYED))
                    } else {
                        rentHistoryRepository.updateRentHistory(updatedRentHistory)
                    }
                }
            }
        }
    }


//    override fun subPoint(memberId: String) {
//        //1. 대여기록중에 userId가 같은 애를 찾아서 leftDate가 0보다 작은 애를 찾음
//        //2. 연체일(leftDate의 절대값)*300만큼 포인트를 차감해줌
//        //3. 변경된 값 업데이트
//        runBlocking {
//            launch {
//                val rentHistories = rentHistoryRepository.getRentHistories()
//                rentHistories.map { m ->
//                    val rentHistory = rentHistoryRepository.getRentHistoryById(m.rentHistoryId).awaitSingle()
//                    if (rentHistory.status == RentHistoryStatusType.DELAYED) {
//                        val pointId = identifierGenerator.generatePointId()
//                        pointRepository.createPointHistory(
//                            Point(
//                                pointId = pointId,
//                                memberId = rentHistory.memberId,
//                                type = PointType.SUB,
//                                totalAmount = abs(300 * rentHistory.leftDate)
//                            )
//                        )
//                    }
//                }
//            }
//        }
//
//    }


}