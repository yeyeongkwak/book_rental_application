package com.group.book_rental_application.application.usecase

import org.springframework.data.domain.Pageable


data class BookQueryCondition(
    var pageable: Pageable? = null,
    val bookName: String = "",
    val author: String = ""
)

data class MemberQueryCondition(
    var memberName:String="",
    var pageable: Pageable?=null
)