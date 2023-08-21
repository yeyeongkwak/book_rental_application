package com.group.book_application.adapters.r2dbc.repository.book

import com.group.book_application.domain.model.Book
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcBookRepository
import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcUserRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.insert
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class R2dbcBookRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcBookRepository: SpringDataR2dbcBookRepository
    ) :BookRepository{
     override suspend fun createBook(book: Book){
        r2dbcTemplate.insert(book).awaitSingle()
    }

    override suspend fun updateBook(book: Book) {
        springDataR2dbcBookRepository.save(book).awaitSingle()
    }

    override suspend fun getBookId(bookId: String): Mono<Book> {
        return springDataR2dbcBookRepository.findByBookId(bookId)
    }

}