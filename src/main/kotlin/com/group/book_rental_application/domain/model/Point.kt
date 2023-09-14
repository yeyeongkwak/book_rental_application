package com.group.book_rental_application.domain.model

import com.group.book_rental_application.domain.enums.PointType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "point")
data class Point(
    @Id
    val pointId: String,
    val memberId: String,
    var type: PointType,
    val changeDate: LocalDateTime= LocalDateTime.now(),
    var totalAmount: Int
) {
//    fun addPointByRent(){
//        totalAmount+=200
//    }
//
    fun minusPointByDelay(delayDays:Int){
        totalAmount-=100*delayDays
    }
}