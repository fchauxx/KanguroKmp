package com.insurtech.kanguro.core.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BankAccountUtilsTest {

    @Test
    fun `Validate routing number`() {
        assertTrue(BankAccountUtils.isValidBankRoutingNumber("021000021"))
        assertTrue(BankAccountUtils.isValidBankRoutingNumber("044115090"))
        assertTrue(BankAccountUtils.isValidBankRoutingNumber("051903761"))

        assertFalse(BankAccountUtils.isValidBankRoutingNumber("02a10000211")) // Must have only numbers
        assertFalse(BankAccountUtils.isValidBankRoutingNumber("021009021")) // Check sum != 0
        assertFalse(BankAccountUtils.isValidBankRoutingNumber("0u10A902c")) // Must have only numbers
        assertFalse(BankAccountUtils.isValidBankRoutingNumber("123456")) // Length != 9
    }

    @Test
    fun `Validate account number`() {
        assertTrue(BankAccountUtils.isValidBankAccountNumber("1234567"))
        assertTrue(BankAccountUtils.isValidBankAccountNumber("123456789014"))

        assertFalse(BankAccountUtils.isValidBankAccountNumber("1234")) // Length < 5
        assertFalse(BankAccountUtils.isValidBankAccountNumber("14a23456B")) // Must have only numbers
    }
}
