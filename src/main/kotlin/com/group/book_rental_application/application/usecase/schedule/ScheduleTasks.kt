package com.group.book_rental_application.application.usecase.schedule

import com.group.book_rental_application.application.usecase.command.handler.CalculateLeftDateExecutor
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleTasks(private val executor: CalculateLeftDateExecutor) {

        private val log= LoggerFactory.getLogger(ScheduleTasks::class.java)
    @Scheduled(cron = "0 49 0 * * *")
    fun calculateLeftDays() {
        executor.subRentDate() //suspend가 걸리면 Scheduled는 파라미터가 없어야 한다고 빌드가 안됨;; 왜지?ㅠ
        log.info("스케쥴러 테스트")
    }
}