package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.UserDataSource
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.networking.dto.AppLanguageDto
import com.insurtech.kanguro.networking.dto.ReminderDto
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto

class FakeUserRemoteDataSource : UserDataSource {

    private var userAccount: UserAccount? = null
    private var codeValidateStatusOperation: Boolean? = null
    private var exception: Exception? = null
    private var language: AppLanguageDto? = null

    fun setUserAccount(userAccount: UserAccount) {
        this.userAccount = userAccount
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    fun setCodeValidateStatusOperation(codeValidateStatusOperation: Boolean) {
        this.codeValidateStatusOperation = codeValidateStatusOperation
    }

    fun getPreferredLanguage(): AppLanguageDto? = language

    override suspend fun updateUserPassword(userUpdatePasswordBodyDto: UserUpdatePasswordBodyDto): Boolean =
        true

    override suspend fun updateUserProfile(
        userUpdateProfileBodyDto: UserUpdateProfileBodyDto
    ): Boolean = true

    override suspend fun updateDeleteUserFlag(requestDeletion: Boolean): Boolean = true

    override suspend fun getUserAccount(): UserAccount = userAccount ?: throw exception!!

    override suspend fun setUserAccount(userAccountBodyDto: UserAccountBodyDto): Boolean = true
    override suspend fun getCodeValidate(email: String, code: String): Boolean =
        codeValidateStatusOperation ?: throw exception!!

    override suspend fun postOtpSms(): Boolean = true

    override suspend fun setPreferredLanguage(language: AppLanguageDto) {
        this.language = language
    }

    override suspend fun getUser(): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun getReminders(): List<ReminderDto> {
        TODO("Not yet implemented")
    }
}
