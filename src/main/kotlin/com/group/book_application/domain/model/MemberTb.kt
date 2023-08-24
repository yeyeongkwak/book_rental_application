package com.group.book_application.domain.model

import com.group.book_application.domain.enums.MemberRankTypes
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table(name = "book_rental.member_tb")
data class Member(
    @Id
    val memberId: String,
    val memberName: String,
    val joinDate: LocalDateTime = LocalDateTime.now(),
    var modifyDate: LocalDateTime? = null,
    var rank: MemberRankTypes,
    var blocked: Boolean = false,
    var totalPoint: Int = 0
) {

}