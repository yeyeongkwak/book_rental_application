package com.group.book_rental_application.adapters.interfaces.rest.dto

import com.group.book_rental_application.domain.enums.PointType

data class CreatePoint(
    val memberId:String,
    val type:PointType
)