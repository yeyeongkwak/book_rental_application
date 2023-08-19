package com.group.book_application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories

class LibraryApplication

fun main(args: Array<String>) {
	runApplication<LibraryApplication>(*args)
}
