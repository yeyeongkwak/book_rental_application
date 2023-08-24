package com.group.book_application.adapters.r2dbc.repository.user

import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcUserRepository
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository

@Repository
class R2dbcUserRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcUserRepository: SpringDataR2dbcUserRepository
) : UserRepository {
    override suspend fun createUser(user: Member) {
        r2dbcTemplate.insert(user).awaitSingle()
    }

    override suspend fun updateUser(user: Member) {
        springDataR2dbcUserRepository.save(user).awaitSingle()
    }

    override suspend fun getUserById(userId: String): Member? {
        return springDataR2dbcUserRepository.findByMemberId(userId).awaitSingleOrNull()
    }

}