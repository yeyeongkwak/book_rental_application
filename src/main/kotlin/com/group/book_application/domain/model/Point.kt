package com.group.book_application.domain.model

import com.group.book_application.domain.enums.PointType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Point(
    @Id
    val pointId:String,
    val userId:String,
    var type: PointType,
    @CreatedDate
    val changeDate:LocalDateTime,
    var totalAmount:Int

) {
}