package com.group.book_rental_application.application.usecase.condition

import org.springframework.data.domain.Pageable

data class MemberQueryCondition(
    var pageable: Pageable? = null,
//    val memberId:String="",
    val memberName: String = ""
)

data class BookQueryCondition(
    var pageable: Pageable? = null,
    val bookName: String = "",
    val author: String = "",
    val purchaseDate: String? = null,
    val publishDate: String? = null
)

data class RentHistoryQueryCondition(
    val pageable: Pageable? = null,
    val memberId: String = "",
    val rentDate: String? = null,
    val returnDate: String? = null,
)