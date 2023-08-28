package com.group.book_application.adapters.interfaces.rest.dto

import com.group.book_application.domain.enums.RentHistoryStatusType

data class UpdateRent(
    val rentHistoryId:String,
    val status:RentHistoryStatusType
)