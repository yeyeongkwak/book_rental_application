package com.group.book_application.repository.book

import com.group.book_application.domain.model.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository

public interface BookRepository : ReactiveCrudRepository<Book, String> { // additional custom query methods go here
}