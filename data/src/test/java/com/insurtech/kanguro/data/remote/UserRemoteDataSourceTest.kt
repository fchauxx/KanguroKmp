package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.data.mapper.UserMapper
import com.insurtech.kanguro.data.source.UserDataSource
import com.insurtech.kanguro.networking.api.RefactoredKanguroUserApiService
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserAccountDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class UserRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var userDataSource: UserDataSource

    @Before fun setUp() {
        val refactoredKanguroUserApiService = restInfrastructureRule.server
            .wireRestApi<RefactoredKanguroUserApiService>()

        userDataSource = UserRemoteDataSource(refactoredKanguroUserApiService)
    }

    @Test fun `Update user password, when it is passed an user update password body and there is some issue incoming from client, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        val userUpdatePasswordBodyDto = UserUpdatePasswordBodyDto(
            email = "mr.kanguro@kanguro.com",
            currentPassword = "K4nguro!",
            newPassword = "K4nguro$"
        )

        // ACT

        val isSuccessfulUpdateUserPasswordOperation = userDataSource
            .updateUserPassword(userUpdatePasswordBodyDto)

        // ASSERT

        assertFalse(isSuccessfulUpdateUserPasswordOperation)
    }

    @Test fun `Update user password, when it is passed an user update password body and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        val userUpdatePasswordBodyDto = UserUpdatePasswordBodyDto(
            email = "mr.kanguro@kanguro.com",
            currentPassword = "K4nguro!",
            newPassword = "K4nguro$"
        )

        // ACT

        val isSuccessfulUpdateUserPasswordOperation = userDataSource
            .updateUserPassword(userUpdatePasswordBodyDto)

        // ASSERT

        assertFalse(isSuccessfulUpdateUserPasswordOperation)
    }

    @Test fun `Update user password, when it is passed an user update password body, then return true`() = runTest {
        // ARRANGE

        val noContentHttpStatusCode = 204

        restInfrastructureRule.restScenario(
            statusCode = noContentHttpStatusCode
        )

        val userUpdatePasswordBodyDto = UserUpdatePasswordBodyDto(
            email = "mr.kanguro@kanguro.com",
            currentPassword = "K4nguro!",
            newPassword = "K4nguro$"
        )

        // ACT

        val isSuccessfulUpdateUserPasswordOperation = userDataSource
            .updateUserPassword(userUpdatePasswordBodyDto)

        // ASSERT

        assertTrue(isSuccessfulUpdateUserPasswordOperation)
    }

    @Test fun `Get user account, when it is requested to get user account and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        // ACT

        val result = kotlin.runCatching {
            userDataSource.getUserAccount()
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get user account, when it is requested to get user account and there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        // ACT

        val result = kotlin.runCatching {
            userDataSource.getUserAccount()
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get user account, when it is requested to get user account, then returns user account successfully`() = runTest {
        // ARRANGE

        val assetFileName = "200_get_get_user_account_successfully.json"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile(assetFileName)
        )

        val expectedResponse = assetFileName.mapJsonToModel<UserAccountDto>()

        if (expectedResponse != null) {
            // ACT

            val response = userDataSource.getUserAccount()

            // ASSERT

            assertEquals(
                expected = UserMapper.mapUserAccountDtoToUserAccount(expectedResponse),
                actual = response
            )
        } else {
            fail("ExpectedResponse must be not null.")
        }
    }

    @Test fun `Set user account, when there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        // ACT

        val isSuccessfulSetUserAccountOperation = userDataSource.setUserAccount(
            UserAccountBodyDto(
                accountNumber = "1234567",
                routingNumber = "021000021",
                bankName = "1st National Bank",
                accountType = AccountType.checking
            )
        )

        // ASSERT

        assertFalse(isSuccessfulSetUserAccountOperation)
    }

    @Test fun `Set user account, when it is passed some user account data as null or empty, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        // ACT

        val isSuccessfulSetUserAccountOperation = userDataSource.setUserAccount(
            UserAccountBodyDto(
                accountNumber = "1234567",
                routingNumber = "",
                bankName = "1st National Bank",
                accountType = AccountType.checking
            )
        )

        assertFalse(isSuccessfulSetUserAccountOperation)
    }

    @Test fun `Set user account, when it is passed all user account data to be updated, then return true`() = runTest {
        // ARRANGE

        val noContentHttpStatusCode = 204

        restInfrastructureRule.restScenario(
            statusCode = noContentHttpStatusCode
        )

        // ACT

        val isSuccessfulSetUserAccountOperation = userDataSource.setUserAccount(
            UserAccountBodyDto(
                accountNumber = "1234567",
                routingNumber = "021000021",
                bankName = "1st National Bank",
                accountType = AccountType.checking
            )
        )

        // ASSERT

        assertTrue(isSuccessfulSetUserAccountOperation)
    }

    @Test fun `Get code validate, when it is passed an email and a code and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        // ACT

        val result = kotlin.runCatching {
            userDataSource.getCodeValidate(
                email = "kanguro@poatek.com",
                code = "012345"
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get code validate, when it is passed an email and a code and there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        // ACT

        val result = kotlin.runCatching {
            userDataSource.getCodeValidate(
                email = "kanguro@poatek.com",
                code = "012345"
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get code validate, when it is passed an email and a code, then returns the status about the operation successfully`() = runTest {
        // ARRANGE

        val email = "kanguro@poatek.com"
        val code = "012345"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("200_get_get_code_validate_successfully.json")
        )

        // ACT

        val response = userDataSource.getCodeValidate(
            email = email,
            code = code
        )

        // ASSERT

        assertTrue(response)
    }

    @Test fun `Post OTP SMS, when it is requested to post OTP SMS and there is some issue incoming from client, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        // ACT

        val isSuccessfulPostOtpSmsOperation = userDataSource.postOtpSms()

        // ASSERT

        assertFalse(isSuccessfulPostOtpSmsOperation)
    }

    @Test fun `Post OTP SMS, when it is requested to post OTP SMS and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        // ACT

        val isSuccessfulPostOtpSmsOperation = userDataSource.postOtpSms()

        // ASSERT

        assertFalse(isSuccessfulPostOtpSmsOperation)
    }

    @Test fun `Post OTP SMS, when it is requested to post OTP SMS, then return true`() = runTest {
        // ARRANGE

        val noContentHttpStatusCode = 200

        restInfrastructureRule.restScenario(
            statusCode = noContentHttpStatusCode
        )

        // ACT

        val isSuccessfulPostOtpSmsOperation = userDataSource.postOtpSms()

        // ASSERT

        assertTrue(isSuccessfulPostOtpSmsOperation)
    }

    @Test fun `Update user profile, when it is passed a user update profile body DTO and there is some issue incoming from client, return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        val givenName = "Mister"
        val surname = "Kanguro"
        val phone = "+5551930202020"

        val userUpdateProfileBodyDto = UserUpdateProfileBodyDto(
            givenName = givenName,
            surname = surname,
            phone = phone
        )

        // ACT

        val isSuccessfulUpdateUserProfileOperation = userDataSource.updateUserProfile(
            userUpdateProfileBodyDto = userUpdateProfileBodyDto
        )

        // ASSERT

        assertFalse(isSuccessfulUpdateUserProfileOperation)
    }

    @Test fun `Update user profile, when it is passed a user update profile body DTO and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        val givenName = "Mister"
        val surname = "Kanguro"
        val phone = "+5551930202020"

        val userUpdateProfileBodyDto = UserUpdateProfileBodyDto(
            givenName = givenName,
            surname = surname,
            phone = phone
        )

        // ACT

        val isSuccessfulUpdateUserProfileOperation = userDataSource.updateUserProfile(
            userUpdateProfileBodyDto = userUpdateProfileBodyDto
        )

        // ASSERT

        assertFalse(isSuccessfulUpdateUserProfileOperation)
    }

    @Test fun `Update user profile, when it is passed a user update profile body DTO, then return true`() = runTest {
        // ARRANGE

        val noContentHttpStatusCode = 204

        restInfrastructureRule.restScenario(
            statusCode = noContentHttpStatusCode
        )

        val givenName = "Mister"
        val surname = "Kanguro"
        val phone = "+5551930202020"

        val userUpdateProfileBodyDto = UserUpdateProfileBodyDto(
            givenName = givenName,
            surname = surname,
            phone = phone
        )

        // ACT

        val isSuccessfulUpdateUserProfileOperation = userDataSource.updateUserProfile(
            userUpdateProfileBodyDto = userUpdateProfileBodyDto
        )

        // ASSERT

        assertTrue(isSuccessfulUpdateUserProfileOperation)
    }

    @Test fun `Update delete user flag, when it is passed a request deletion and there is some issue incoming from client, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        // ACT

        val isSuccessfulUpdateDeleteUserFlagOperation = userDataSource.updateDeleteUserFlag(true)

        // ASSERT

        assertFalse(isSuccessfulUpdateDeleteUserFlagOperation)
    }

    @Test fun `Update delete user flag, when it is passed a request deletion and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        // ACT

        val isSuccessfulUpdateDeleteUserFlagOperation = userDataSource.updateDeleteUserFlag(true)

        // ASSERT

        assertFalse(isSuccessfulUpdateDeleteUserFlagOperation)
    }

    @Test fun `Update delete user flag, when it is passed a request deletion, then return true`() = runTest {
        // ARRANGE

        val noContentHttpStatusCode = 204

        restInfrastructureRule.restScenario(
            statusCode = noContentHttpStatusCode
        )

        // ACT

        val isSuccessfulUpdateDeleteUserFlagOperation = userDataSource.updateDeleteUserFlag(true)

        // ASSERT

        assertTrue(isSuccessfulUpdateDeleteUserFlagOperation)
    }
}
