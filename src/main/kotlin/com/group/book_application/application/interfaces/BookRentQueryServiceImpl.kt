package com.group.book_application.application.interfaces

import com.group.book_application.application.usecase.BookQueryCondition
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.repository.BookRepository
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service

interface BookRentQueryService {
    suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book>
}

@Service
class BookRentQueryServiceImpl(
    private val bookRepository: BookRepository
) : BookRentQueryService {
    override suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book> {
        val books = bookRepository.getAllBooks()
        val count = books.size
        return PageImpl(books, condition.pageable!!, count.toLong())

    }
}