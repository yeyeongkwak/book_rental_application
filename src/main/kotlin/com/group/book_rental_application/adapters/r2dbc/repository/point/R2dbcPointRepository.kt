package com.group.book_rental_application.adapters.r2dbc.repository.point

import com.group.book_rental_application.adapters.r2dbc.repository.SpringDataR2dbcPointRepository
import com.group.book_rental_application.domain.model.Point
import com.group.book_rental_application.domain.repository.PointRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcPointRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcPointRepository: SpringDataR2dbcPointRepository
) : PointRepository {
    override suspend fun createPointHistory(point: Point) {
        r2dbcTemplate.insert(point).awaitSingle()
    }

    override suspend fun createPointHistories(points: List<Point>) {
//        r2dbcTemplate.insert(points).awaitSingle()
        springDataR2dbcPointRepository.saveAll(points)
    }

    override suspend fun getPointById(pointId: String): Mono<Point> {
        return springDataR2dbcPointRepository.findByPointId(pointId)
    }

    override suspend fun searchPointByMemberId(memberId: String): List<Point> {
        return springDataR2dbcPointRepository.findAllByMemberId(memberId).asFlow().toList() // 확인해볼것.
    }
}