package com.group.book_rental_application.adapters.interfaces.rest.config

import com.group.book_rental_application.adapters.interfaces.rest.BookRentCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BookRentCommandRouterConfig(private val bookRentCommandHandler: BookRentCommandHandler) {
    @Bean
    fun bookRentCommandRouter() = coRouter {
        "/api/rent".nest {
            accept(MediaType("application", "vnd.xc.v1+json")).nest {
                POST("/member", bookRentCommandHandler::createUser)
                POST("/book", bookRentCommandHandler::createBook)
//                POST("/histories",bookRentCommandHandler::createRentHistory)
                POST("/{memberId}/histories", bookRentCommandHandler::createRentHistories) // 순서
                PUT("/{memberId}/histories",bookRentCommandHandler::updateRent) // 리소스 순서
            }
        }
    }
}