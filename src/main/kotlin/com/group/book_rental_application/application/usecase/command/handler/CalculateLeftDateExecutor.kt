package com.group.book_rental_application.application.usecase.command.handler

import com.group.book_rental_application.adapters.r2dbc.repository.R2dbcIdentifierGenerator
import com.group.book_rental_application.domain.enums.PointType
import com.group.book_rental_application.domain.enums.RentHistoryStatusType
import com.group.book_rental_application.domain.model.Point
import com.group.book_rental_application.domain.repository.BookRepository
import com.group.book_rental_application.domain.repository.MemberRepository
import com.group.book_rental_application.domain.repository.PointRepository
import com.group.book_rental_application.domain.repository.RentHistoryRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period


interface CalculateLeftDateExecutor {
    fun subRentDate()
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

                rentHistories.filter { r ->
                    r.status === RentHistoryStatusType.RETURNED
                }.map { r -> rentHistoryRepository.updateRentHistory(r.apply { leftDate = 0 }) }

                //반납된 애들이 아닌 애들만
                rentHistories.filterNot {
                    it.status === RentHistoryStatusType.RETURNED
                }.map { m ->
                    val newLeftDate = m.leftDate-1
                    val sameUser = memberRepository.getMemberByMemberId(m.memberId).awaitSingle()

                    //남은 일수에 따라 대여 이력 상태 변경하기
                    m.changeStatus(newLeftDate)
                    rentHistoryRepository.updateRentHistory(m)
                    if (m.status == RentHistoryStatusType.DELAYED) {
                        val rentBook = bookRepository.getBookId(m.rentHistoryId).awaitSingle()
                        rentBook.delayReturnBooks()
                        bookRepository.updateBook(rentBook)
                            .apply {
                                //포인트 차감 히스토리 생성
                                val pointId = identifierGenerator.generatePointId()
                                pointRepository.createPointHistory(
                                    Point(
                                        pointId = pointId,
                                        memberId = m.memberId,
                                        type = PointType.SUB,
                                        totalAmount = -300
                                    )
                                    )
                                //차감된 내역까지의 합을 구해서 유저의 포인트 총합을 업데이트
                                val updatedPoint =
                                    pointRepository.searchPointByMemberId(m.memberId).sumOf { p -> p.totalAmount }
                                sameUser.updatePoint(updatedPoint)
                            }.apply {
                                sameUser.updateBlockStatus() //-1000p일때 회원 자격 정지가 될지 유무
                                sameUser.updateRank() //포인트 차감시 등급이 강등될지 유무
                                sameUser.updateMaxRentCount() //등급 강등될 시 최대 대여가능 갯수 업데이트
                                memberRepository.updateUser(sameUser) //최종 저장
                            }
                    }
                    else {
                        rentHistoryRepository.updateRentHistory(m)
                    }
                }
            }
        }
    }
}