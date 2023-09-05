package com.group.book_rental_application.utils

import com.group.book_rental_application.application.dto.PageableTotalDTO
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

object ResponseUtils {
    suspend fun ok(): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .buildAndAwait()
    }

    suspend fun <T : Any> ok(body:T):ServerResponse{
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }

    suspend fun create(uri: String): ServerResponse {
        return ServerResponse
            .created(URI.create(uri))
            .contentType(MediaType.APPLICATION_JSON)
            .buildAndAwait()
    }

    suspend fun <T> ok(pageable: PageImpl<T>): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Total-Page", pageable.totalPages.toString())
            .header("X-Total", pageable.totalElements.toString())
            .bodyValueAndAwait(pageable.content)
    }

    suspend fun <T : Any> ok(pageable: PageableTotalDTO<T>): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Total-Page", pageable.totalPages.toString())
            .header("X-Total", pageable.totalElements.toString())
            .bodyValueAndAwait(pageable.content)
    }
}