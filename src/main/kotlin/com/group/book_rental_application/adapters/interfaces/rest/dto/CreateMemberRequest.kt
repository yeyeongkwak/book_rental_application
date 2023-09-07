package com.group.book_rental_application.adapters.interfaces.rest.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateMemberRequest(
    @field:Schema(description = "유저ID", required = true)
    val memberId: String,
    @field:Schema(description = "유저 이름", required = true)
    val memberName: String

)