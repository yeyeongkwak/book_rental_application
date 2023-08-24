package com.group.book_application.adapters.interfaces.rest.dto

import com.group.book_application.domain.enums.MemberRankTypes
import io.swagger.v3.oas.annotations.media.Schema

data class CreateMemberRequest(
    @field:Schema(description = "유저ID", required = true)
    var memberId: String,
    @field:Schema(description = "유저 이름", required = true)
    var memberName: String,
    @field:Schema(description = "유저 등급", required = true)
    var rank: MemberRankTypes

)