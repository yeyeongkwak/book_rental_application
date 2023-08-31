package com.group.book_application.adapters.interfaces.rest.config

import com.group.book_application.adapters.interfaces.rest.BookRentCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
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
                POST("/histories/{memberId}", bookRentCommandHandler::createRentHistories)
                PUT("/histories/{memberId}",bookRentCommandHandler::updateRent)
            }
        }
    }
}