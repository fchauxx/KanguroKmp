package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserAccountDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class UserMapperTest {

    @Test fun `Map user account DTO to user account successfully`() {
        // ARRANGE

        val userAccountDto = "200_get_get_user_account_successfully.json"
            .mapJsonToModel<UserAccountDto>()

        // ACT

        if (userAccountDto != null) {
            val userAccount = UserMapper.mapUserAccountDtoToUserAccount(userAccountDto)

            // ASSERT

            assertEquals(
                expected = UserAccount(
                    accountNumber = "1234567",
                    routingNumber = "021000021",
                    bankName = "1st National Bank",
                    accountType = AccountType.checking
                ),
                actual = userAccount
            )
        } else {
            fail("UserAccountDto must be not null.")
        }
    }

    @Test fun `Map user account to user account body DTO`() {
        // ARRANGE

        val userAccount = "200_get_get_user_account_successfully.json"
            .mapJsonToModel<UserAccount>()

        // ACT

        if (userAccount != null) {
            val userAccountBodyDto = UserMapper
                .mapUserAccountToUserAccountBodyDto(userAccount)

            // ASSERT

            assertEquals(
                expected = UserAccountBodyDto(
                    accountNumber = "1234567",
                    routingNumber = "021000021",
                    bankName = "1st National Bank",
                    accountType = AccountType.checking
                ),
                actual = userAccountBodyDto
            )
        } else {
            fail("UserAccount must be not null.")
        }
    }

    @Test fun `Map user account to user account body DTO, when bank name and account type are null, then verify these attributes are correct in UserAccountBodyDTO`() {
        // ARRANGE

        val userAccount = UserAccount(
            accountNumber = "1234567",
            routingNumber = "021000021",
            bankName = null,
            accountType = null
        )

        // ACT

        val userAccountBodyDto = UserMapper
            .mapUserAccountToUserAccountBodyDto(userAccount)

        // ASSERT

        assertEquals(
            expected = UserAccountBodyDto(
                accountNumber = "1234567",
                routingNumber = "021000021",
                bankName = "",
                accountType = AccountType.checking
            ),
            actual = userAccountBodyDto
        )
    }

    @Test fun `Map user update password body to user update password body DTO successfully`() {
        // ARRANGE

        val userUpdatePasswordBody = UserUpdatePasswordBody(
            email = "mr.kanguro@kanguro.com",
            currentPassword = "K4nguro!",
            newPassword = "K4nguro$"
        )

        // ACT

        val userUpdatePasswordBodyDto = UserMapper
            .mapUserUpdatePasswordBodyToUserUpdatePasswordBodyDto(userUpdatePasswordBody)

        // ASSERT

        assertEquals(
            expected = UserUpdatePasswordBodyDto(
                email = "mr.kanguro@kanguro.com",
                currentPassword = "K4nguro!",
                newPassword = "K4nguro$"
            ),
            actual = userUpdatePasswordBodyDto
        )
    }

    @Test fun `Map user update profile body to user update profile body DTO successfully`() {
        // ARRANGE

        val givenName = "Mister"
        val surname = "Kanguro"
        val phone = "+5551930202020"

        val userUpdateProfileBody = UserUpdateProfileBody(
            givenName = givenName,
            surname = surname,
            phone = phone
        )

        // ACT

        val userUpdateProfileBodyDto = UserMapper
            .mapUserUpdateProfileBodyToUserUpdateProfileBodyDto(userUpdateProfileBody)

        // ASSERT

        assertEquals(
            expected = UserUpdateProfileBodyDto(
                givenName = givenName,
                surname = surname,
                phone = phone
            ),
            actual = userUpdateProfileBodyDto
        )
    }
}
