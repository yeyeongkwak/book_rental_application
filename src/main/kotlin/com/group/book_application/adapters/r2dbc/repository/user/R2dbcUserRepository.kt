package com.group.book_application.adapters.r2dbc.repository.user

import com.group.book_application.domain.model.User
import com.group.book_application.domain.repository.UserRepository
import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcUserRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcUserRepository(
    val r2dbcTemplate:R2dbcEntityTemplate,
    val springDataR2dbcUserRepository: SpringDataR2dbcUserRepository
) :UserRepository{
    override suspend fun createUser(user: User){
        r2dbcTemplate.insert(user).awaitSingle()
    }

    override suspend fun updateUser(user: User) {
        springDataR2dbcUserRepository.save(user).awaitSingle()
    }

    override suspend fun getUserById(userId: String): Mono<User> {
        return springDataR2dbcUserRepository.findByUserId(userId)
    }
}