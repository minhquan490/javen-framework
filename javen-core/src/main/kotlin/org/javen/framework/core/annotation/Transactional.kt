package org.javen.framework.core.annotation

import java.sql.Connection

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Transactional(val isolation: Isolation = Isolation.DEFAULT, val timeout: Int = -1) {
    enum class Isolation(private val level: Int) {
        DEFAULT(-1),
        NOT_SUPPORTED(Connection.TRANSACTION_NONE),
        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

        override fun toString(): String {
            return name
        }

        fun getIsolationLevel(): Int {
            return this.level
        }
    }
}
