package com.group.book_application.domain.model

import com.group.book_application.domain.enums.PointType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Point(
    @Id
    val pointId:String,
    val memberId:String,
    var type: PointType,
    @CreatedDate
    val changeDate:LocalDateTime,
    var totalAmount:Int

) {
}