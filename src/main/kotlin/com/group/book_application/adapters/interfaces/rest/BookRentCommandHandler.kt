package com.group.book_application.adapters.interfaces.rest

import com.group.book_application.adapters.interfaces.rest.dto.CreateBookRequest
import com.group.book_application.adapters.interfaces.rest.dto.CreateMemberRequest
import com.group.book_application.application.interfaces.BookRentCommandService
import com.group.book_application.dto.user.UserRequest
import com.group.book_application.utils.ResponseUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody

@Component
class BookRentCommandHandler(
    val bookRentCommandService:BookRentCommandService
){

    suspend fun createUser(req:ServerRequest): ServerResponse =req.awaitBody<CreateMemberRequest>()
        .let {
        ResponseUtils.create(
            "/api/rent/${bookRentCommandService.createUser(it)}"
        )
    }

    suspend fun createBook(req:ServerRequest):ServerResponse=req.awaitBody<CreateBookRequest>()
        .let {
            ResponseUtils.create(
                "/api/rent/${bookRentCommandService.createBook(it)}"
            )
        }

}