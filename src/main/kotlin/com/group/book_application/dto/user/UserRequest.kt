package com.group.book_application.dto.user

import com.group.book_application.domain.enums.MemberRankTypes

data class UserRequest (
    var userId:String,
    var userName:String,
)