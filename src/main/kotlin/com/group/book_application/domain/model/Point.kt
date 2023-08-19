package com.group.book_application.domain.model

import com.group.book_application.domain.enums.PointType
import jakarta.persistence.Id
import java.time.LocalDateTime

class Point(
    @Id
    val pointId:String,
    val userId:String,
    var type: PointType,
    val changeDate:LocalDateTime,
    var totalAmount:Int

) {
}