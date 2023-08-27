package com.group.book_application.domain.model

import com.group.book_application.domain.enums.MemberRankTypes
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table(name = "book_rental.member")
data class Member(
    @Id
    val memberId: String,   //멤버 이름
    val memberName: String,  //회원 이름
    val joinDate: LocalDateTime = LocalDateTime.now(), //가입날짜
    var modifyDate: LocalDateTime? = null, //수정날짜
    var rank: MemberRankTypes = MemberRankTypes.BRONZE, //멤버의 등급
    var blocked: Boolean = false, //-1000 포인트가 되어 자격정지가 되었는지에 대한 유무
    var totalPoint: Int = 0,  //유저가 보유한 총 포인트
    var maxRentCount: Int = 5,  //등급별 대여제한 수
    var currentRentCount: Int = 0  //현재 유저가 대여한 책의 개수
) {
    fun updateMaxRentCount(member:Member) {
        when (member.rank) {
            MemberRankTypes.BRONZE -> maxRentCount = 5
            MemberRankTypes.SILVER -> maxRentCount = 10
            MemberRankTypes.GOLD -> maxRentCount = 15
            MemberRankTypes.VIP -> maxRentCount = 20
        }
    }

    fun updateRank(member:Member) {
        when {
            member.totalPoint in 2000..4999 -> rank = MemberRankTypes.SILVER
            member.totalPoint in 5000..9999 -> rank = MemberRankTypes.GOLD
            member.totalPoint >= 10000 -> rank = MemberRankTypes.VIP
            else -> rank = MemberRankTypes.BRONZE
        }
    }

    fun updateCurrentRentCount(member: Member,rentCount: Int) {
        when {
            //빌리려는 책 합이 대여제한 수를 넘으면 에러 throw
            member.maxRentCount < currentRentCount+rentCount -> throw Exception()
            else -> {
                currentRentCount += rentCount
            }
        }
    }

    fun updateBlockStatus(member: Member){
        when{
            member.totalPoint<=-1000->blocked=true
        }
    }

    fun updatePoint(point: Int){
        totalPoint+=point
    }
}