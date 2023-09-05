package com.group.book_rental_application.adapters.r2dbc.repository.book

import com.group.book_rental_application.adapters.r2dbc.repository.SpringDataR2dbcBookRepository
import com.group.book_rental_application.domain.model.Book
import com.group.book_rental_application.domain.repository.BookRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class R2dbcBookRepository(
    val r2dbcTemplate: R2dbcEntityTemplate,
    val springDataR2dbcBookRepository: SpringDataR2dbcBookRepository
) : BookRepository {
    override suspend fun createBook(book: Book) {
        r2dbcTemplate.insert(book).awaitSingle()
    }

    override suspend fun updateBook(book: Book) {
        springDataR2dbcBookRepository.save(book).awaitSingle()
    }

    override suspend fun updateBooks(books: List<Book>) {
        springDataR2dbcBookRepository.saveAll(books).asFlow().toList()
    }

    override suspend fun getBookId(bookId: String): Mono<Book> {
        return springDataR2dbcBookRepository.findByBookId(bookId)
    }

    override suspend fun getAllBooks(): List<Book> {
        return springDataR2dbcBookRepository.findAll().asFlow().toList()
    }

    override suspend fun getByBookIds(bookIds: List<String>): Flux<Book> {
        return springDataR2dbcBookRepository.findByBookIdIsIn(bookIds)
    }

    override suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): List<Book> {
        val criteria = where("book_name").like("%$bookName%")
            .and("author").like("%$author%")
        return r2dbcTemplate.select(query(criteria), Book::class.java).asFlow().toList()
    }


}