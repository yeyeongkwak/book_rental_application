package com.group.book_application.adapters.interfaces.rest.config

import com.group.book_application.adapters.interfaces.rest.BookRentQueryHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BookRentQueryRouterConfig(private val bookRentQueryHandler: BookRentQueryHandler) {
    @Bean
    fun bookRentQueryRouter()= coRouter {
        "/api/rent".nest{
            /*Query*/
            GET("/book",bookRentQueryHandler::getAllBooks) //::=>리플랙션(참조)를 위해 사용
        }
    }

}
