package com.group.book_application.application.interfaces
import com.group.book_application.application.usecase.BookQueryCondition
import com.group.book_application.domain.model.Book
import com.group.book_application.domain.model.Member
import com.group.book_application.domain.repository.BookRepository
import com.group.book_application.domain.repository.MemberRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


interface BookRentQueryService {
    suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member>
//    suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book>
    suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book>
}

@Service
class BookRentQueryServiceImpl(
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository
) : BookRentQueryService {

    override suspend fun searchMembers(memberName: String, pageable: Pageable): PageImpl<Member> {
        val searchMemberName = memberRepository.searchMembers(memberName, pageable);
        return PageImpl(searchMemberName, pageable, searchMemberName.size.toLong())
    }

//    override suspend fun getAllBooks(condition: BookQueryCondition): PageImpl<Book> {
//        val books = bookRepository.getAllBooks()
//        val count = books.size
//        return PageImpl(books, condition.pageable!!, count.toLong())
//
//    }

    override suspend fun searchBooks(bookName: String, author: String, pageable: Pageable): PageImpl<Book> {

        val books = bookRepository.searchBooks(bookName, author, pageable)
        return PageImpl(books, pageable, books.size.toLong())
    }
}