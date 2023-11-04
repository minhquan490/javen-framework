package org.javen.framework.utils

import java.util.regex.Pattern

class ValidateUtils private constructor() {

    private object Rfc2822 {
        private const val REGEX =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
        private val PATTERN: Pattern = Pattern.compile(REGEX)
        fun validate(email: String?): Boolean? {
            return email?.let { PATTERN.matcher(it).matches() }
        }
    }


    private object Rfc5322 {
        private const val REGEX =
            "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        private val PATTERN: Pattern = Pattern.compile(REGEX)
        fun validate(email: String?): Boolean? {
            return email?.let { PATTERN.matcher(it).matches() }
        }
    }

    companion object {
        private const val PATTER = "(84|0[3|5|7|8|9])+([0-9]{8})\\b"
        private val PATTERN: Pattern = Pattern.compile(PATTER)
        private val NUMBER_PATTERN: Pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$")
        private val URL_PATTERN: Pattern =
            Pattern.compile("((https?):\\/\\/)?(www.)?[a-z0-9]+(\\.[a-z]{2,}){1,3}(#?\\/?[a-zA-Z0-9#]+)*\\/?(\\?[a-zA-Z0-9-_]+=[a-zA-Z0-9-%]+&?)?$")
        private val PRODUCT_SIZE_PATTERN: Pattern = Pattern.compile("^[S|s]|[M|m]|[L|l]|([X|x]{1,}[L|l])$")
        private val DATE_PATTERN: Pattern =
            Pattern.compile("^\\d{4}-?\\d{1,2}-?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,6}$")

        fun isEmailValidUsingRfc2822(email: String?): Boolean? {
            return if (email == null) {
                false
            } else Rfc2822.validate(email)
        }

        fun isEmailValidUsingRfc5322(email: String?): Boolean? {
            return if (email == null) {
                false
            } else Rfc5322.validate(email)
        }

        fun isPhoneValid(phone: String?): Boolean {
            return if (phone == null) {
                false
            } else PATTERN.matcher(phone).matches()
        }

        fun isUrlValid(url: String?): Boolean {
            return if (url == null) {
                false
            } else URL_PATTERN.matcher(url).matches()
        }

        fun isSizeValid(productSize: String?): Boolean {
            return if (productSize == null) {
                false
            } else PRODUCT_SIZE_PATTERN.matcher(productSize).matches()
        }

        fun isValidDate(testString: String?): Boolean {
            return if (testString == null) {
                false
            } else DATE_PATTERN.matcher(testString).matches()
        }

        fun isNumber(number: String?): Boolean {
            return if (number == null) {
                false
            } else NUMBER_PATTERN.matcher(number).matches()
        }
    }
}