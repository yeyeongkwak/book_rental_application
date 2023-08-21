package com.group.book_application.adapters.r2dbc.repository.renthistory

import com.group.book_application.adapters.r2dbc.repository.SpringDataR2dbcRentHistory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

class R2dbcRentHistoryRepository(
    val r2dbcTemplate:R2dbcEntityTemplate,
    val springDataR2dbcRentHistory: SpringDataR2dbcRentHistory
) {

}