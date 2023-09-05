package com.group.book_rental_application.adapters.r2dbc.repository.user

import com.group.book_rental_application.adapters.r2dbc.repository.SpringDataR2dbcMemberRepository
import com.group.book_rental_application.domain.model.Member
import com.group.book_rental_application.domain.repository.MemberRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcMemberRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcMemberRepository: SpringDataR2dbcMemberRepository
) : MemberRepository {
    override suspend fun createUser(user: Member) {
        r2dbcTemplate.insert(user).awaitSingle()
    }

    override suspend fun updateUser(user: Member) {
        springDataR2dbcMemberRepository.save(user).awaitSingle()
    }

    override suspend fun getMemberByMemberId(memberId: String): Mono<Member> {
        return springDataR2dbcMemberRepository.findByMemberId(memberId)
    }

    override suspend fun getMemberByName(memberName:String, pageable: Pageable): List<Member> {
        return springDataR2dbcMemberRepository.findMemberByMemberName(memberName, pageable).asFlow().toList()

    }

//    override suspend fun searchMembers(memberName: String, pageable: Pageable): List<Member> {
//        val criteria=where("member_name").like("%$memberName%")
//        return r2dbcTemplate.select(query(criteria),Member::class.java).asFlow().toList()
//    }
    override suspend fun searchMembers(memberName: String, pageable: Pageable): List<Member> {
        val criteria=where("member_name").like("%$memberName%")
        return r2dbcTemplate.select(query(criteria),Member::class.java).asFlow().toList()
    }


}