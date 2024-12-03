package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.UserMapper
import com.insurtech.kanguro.data.source.UserDataSource
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.networking.api.RefactoredKanguroUserApiService
import com.insurtech.kanguro.networking.dto.AppLanguageDto
import com.insurtech.kanguro.networking.dto.ReminderDto
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserDto
import com.insurtech.kanguro.networking.dto.UserUpdateLanguagePreferenceBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val refactoredKanguroUserApiService: RefactoredKanguroUserApiService
) : UserDataSource {

    override suspend fun updateUserPassword(
        userUpdatePasswordBodyDto: UserUpdatePasswordBodyDto
    ): Boolean =
        try {
            val response = refactoredKanguroUserApiService
                .putUpdatePassword(userUpdatePasswordBodyDto)

            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun updateUserProfile(
        userUpdateProfileBodyDto: UserUpdateProfileBodyDto
    ): Boolean =
        try {
            val response = refactoredKanguroUserApiService
                .putUpdateUserProfile(userUpdateProfileBodyDto)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun updateDeleteUserFlag(requestDeletion: Boolean): Boolean =
        try {
            val response = refactoredKanguroUserApiService.putDeleteUserFlag(
                requestDeletion = requestDeletion
            )
            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun getUserAccount(): UserAccount =
        managedExecution {
            UserMapper.mapUserAccountDtoToUserAccount(
                refactoredKanguroUserApiService.getUserAccount()
            )
        }

    override suspend fun setUserAccount(userAccountBodyDto: UserAccountBodyDto): Boolean =
        try {
            val response = refactoredKanguroUserApiService
                .putUserAccount(userAccountBodyDto)

            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun getCodeValidate(email: String, code: String): Boolean =
        managedExecution {
            refactoredKanguroUserApiService.getCodeValidate(
                email = email,
                code = code
            )
        }

    override suspend fun postOtpSms(): Boolean =
        try {
            val response = refactoredKanguroUserApiService.postOtpSms()

            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun setPreferredLanguage(language: AppLanguageDto): Unit = managedExecution {
        refactoredKanguroUserApiService.putUserPreferredLanguage(
            UserUpdateLanguagePreferenceBodyDto(language)
        )
    }

    override suspend fun getUser(): UserDto = managedExecution {
        refactoredKanguroUserApiService.getUser()
    }

    override suspend fun getReminders(): List<ReminderDto> = managedExecution {
        refactoredKanguroUserApiService.getReminders()
    }
}
