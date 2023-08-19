package com.group.book_application.repository.user

import com.group.book_application.domain.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository

public interface UserRepository:ReactiveCrudRepository<User,String> {

}