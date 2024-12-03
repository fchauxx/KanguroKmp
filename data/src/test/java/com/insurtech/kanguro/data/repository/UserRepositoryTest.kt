package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.remote.fakes.FakePetRemoteDataSource
import com.insurtech.kanguro.data.remote.fakes.FakeUserRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.RefactoredUserRepository
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class UserRepositoryTest {

    private lateinit var fakeUserRemoteDataSource: FakeUserRemoteDataSource

    private lateinit var fakePetRemoteDataSource: FakePetRemoteDataSource

    private lateinit var refactoredUserRepository: IRefactoredUserRepository

    @Before fun setUp() {
        fakeUserRemoteDataSource = FakeUserRemoteDataSource()
        fakePetRemoteDataSource = FakePetRemoteDataSource()

        refactoredUserRepository = RefactoredUserRepository(
            userRemoteDataSource = fakeUserRemoteDataSource,
            petDataSource = fakePetRemoteDataSource
        )
    }

    @Test fun `Update user password, when it is passed an user update password body, then return success with true`() = runTest {
        // ARRANGE

        val userUpdatePasswordBody = UserUpdatePasswordBody(
            email = "mr.kanguro@kanguro.com",
            currentPassword = "K4nguro!",
            newPassword = "K4nguro$"
        )

        // ACT

        val result = refactoredUserRepository.updateUserPassword(
            userUpdatePasswordBody = userUpdatePasswordBody
        )

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }

    @Test fun `Update user profile, when it is passed an user update profile body, then return success with true`() = runTest {
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

        val result = refactoredUserRepository.updateUserProfile(userUpdateProfileBody)

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }

    @Test fun `Get user account, when it is requested to get user account and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeUserRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        refactoredUserRepository.getUserAccount().test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get user account, when it is requested to get user account, then return result success with user account`() = runTest {
        // ARRANGE

        val expectedUserAccount = "200_get_get_user_account_successfully.json"
            .mapJsonToModel<UserAccount>()

        if (expectedUserAccount != null) {
            fakeUserRemoteDataSource.setUserAccount(expectedUserAccount)

            // ACT / ASSERT

            refactoredUserRepository.getUserAccount().test {
                assertEquals(
                    expected = Result.Success(expectedUserAccount),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("ExpectedUserAccount must be not null.")
        }
    }

    @Test fun `Set user account, when it is passed all user account data, then return result success with true`() = runTest {
        // ACT

        val result = refactoredUserRepository.setUserAccount(
            UserAccount(
                accountNumber = "1234567",
                routingNumber = "021000021",
                bankName = "1st National Bank",
                accountType = AccountType.checking
            )
        )

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }

    @Test fun `Get code validate, when it is passed an email and a code and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeUserRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        refactoredUserRepository.getCodeValidate(
            email = "kanguro@poatek.com",
            code = "012345"
        ).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get code validate, when it is passed an email and a code, then return result success with the status operation`() = runTest {
        // ARRANGE

        fakeUserRemoteDataSource.setCodeValidateStatusOperation(true)

        // ACT / ASSERT

        refactoredUserRepository.getCodeValidate(
            email = "kanguro@poatek.com",
            code = "012345"
        ).test {
            assertEquals(
                expected = Result.Success(true),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Post OTP SMS, when it is requested to post OTP SMS, then return result success with true`() = runTest {
        // ACT

        val result = refactoredUserRepository.postOtpSms()

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }

    @Test fun `Update delete user flag, when it is passed a request deletion, then return result success with true`() = runTest {
        // ACT

        val result = refactoredUserRepository.updateDeleteUserFlag(true)

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }
}
