package org.javen.framework.utils

import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DateTimeUtils private constructor() {
    companion object {
        private var DATE_TIME_FORMATTER: DateTimeFormatter? = null
        private var DATE_FORMATTER: DateTimeFormatter? = null

        init {
            DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
            DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        }

        fun convertOutputDateTime(target: Timestamp): String {
            return convertOutputDateTime(target.toLocalDateTime())
        }

        fun convertOutputDateTime(target: LocalDateTime?): String {
            return DATE_TIME_FORMATTER!!.format(target)
        }

        fun convertOutputDate(target: LocalDate?): String {
            return DATE_FORMATTER!!.format(target)
        }

        fun convertOutputDate(target: Date): String {
            return convertOutputDate(target.toLocalDate())
        }

        fun convertOutputDate(target: Timestamp): String {
            return convertOutputDate(target.toLocalDateTime().toLocalDate())
        }

        fun calculateTimeRefreshTokenExpired(timeCreated: Instant): Timestamp {
            return Timestamp.from(Instant.ofEpochSecond(timeCreated.epochSecond + 86400 * 365))
        }

    }
}