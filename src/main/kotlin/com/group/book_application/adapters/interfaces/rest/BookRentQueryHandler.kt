package com.group.book_application.adapters.interfaces.rest

import com.group.book_application.application.interfaces.BookRentQueryService
import com.group.book_application.application.usecase.MemberQueryCondition
import com.group.book_application.utils.ResponseUtils
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class BookRentQueryHandler(
    private val bookRentQueryService: BookRentQueryService
) {
    suspend fun searchMembers(req: ServerRequest): ServerResponse {
        val memberName = req.queryParam("memberName").orElse("")
        val page = req.queryParam("page").orElse("1").toInt()
        val size = req.queryParam("size").orElse("10").toInt()
        return MemberQueryCondition(
            memberName = memberName,
            pageable = PageRequest.of(
                page - 1, size
            )
        ).let {
            ResponseUtils.ok(bookRentQueryService.searchMembers(it.memberName, it.pageable!!))
        }
    }

//    suspend fun getAllBooks(req: ServerRequest): ServerResponse {
//        val bookName = req.queryParam("bookName").orElse("")
//        val author = req.queryParam("author").orElse("")
//        val page = req.queryParam("page").orElse("1").toInt()
//        val size = req.queryParam("size").orElse("10").toInt()
//
//        return BookQueryCondition(
//            bookName = bookName,
//            author = author,
//            pageable = PageRequest.of(
//                page - 1, size
//            )
//        ).let {
//            ResponseUtils.ok(bookRentQueryService.getAllBooks(it))
//
//        }
//    }

    suspend fun searchBooks(req: ServerRequest): ServerResponse {
        val bookName = req.queryParam("bookName").orElse("")
        val author = req.queryParam("author").orElse("")
        val page = req.queryParam("page").orElse("1").toInt()
        val size = req.queryParam("size").orElse("10").toInt()


        return ResponseUtils.ok(
            bookRentQueryService.searchBooks(
                bookName = bookName,
                author = author,
                pageable = PageRequest.of(
                    page - 1, size
                )
            )
        )
    }
}
