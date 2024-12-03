package com.insurtech.kanguro.core.utils

object BankAccountUtils {

    fun isValidBankRoutingNumber(routingNumber: String): Boolean {
        val regex = "^[0-9]{9}$".toRegex()
        return routingNumber.matches(regex) && checkRoutingNumberChecksum(routingNumber)
    }

    fun isValidBankAccountNumber(accountNumber: String): Boolean {
        val regex = "^[0-9]{5,20}$".toRegex()
        return accountNumber.matches(regex)
    }

    private fun checkRoutingNumberChecksum(routingNumber: String): Boolean {
        if (routingNumber.length != 9) {
            return false
        }

        val mappedRoutingNumber = routingNumber.mapNotNull { it.digitToIntOrNull() }

        if (mappedRoutingNumber.size != 9) {
            return false
        }

        val sum = (3 * (mappedRoutingNumber[0] + mappedRoutingNumber[3] + mappedRoutingNumber[6])) +
            (
                7 * (mappedRoutingNumber[1] + mappedRoutingNumber[4] + mappedRoutingNumber[7]) +
                    (mappedRoutingNumber[2] + mappedRoutingNumber[5] + mappedRoutingNumber[8])
                )

        if (sum % 10 == 0) {
            return true
        }

        return false
    }
}
