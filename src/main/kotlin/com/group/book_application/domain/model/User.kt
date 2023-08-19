package com.group.book_application.domain.model

import com.group.book_application.domain.enums.MemberRankTypes
import jakarta.persistence.Table
import org.springframework.data.annotation.Id
import java.time.LocalDateTime


@Table
data class User(
    @Id
    val userId:String,
    val userName:String,
    val joinDate:LocalDateTime,
    var modifyDate:LocalDateTime,
    var rank: MemberRankTypes,
    var blocked:Boolean
) {

}