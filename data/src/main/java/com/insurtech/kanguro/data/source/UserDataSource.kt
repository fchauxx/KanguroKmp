package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.networking.dto.AppLanguageDto
import com.insurtech.kanguro.networking.dto.ReminderDto
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto

interface UserDataSource {

    suspend fun updateUserPassword(
        userUpdatePasswordBodyDto: UserUpdatePasswordBodyDto
    ): Boolean

    suspend fun updateUserProfile(
        userUpdateProfileBodyDto: UserUpdateProfileBodyDto
    ): Boolean

    suspend fun updateDeleteUserFlag(requestDeletion: Boolean): Boolean

    suspend fun getUserAccount(): UserAccount

    suspend fun setUserAccount(userAccountBodyDto: UserAccountBodyDto): Boolean

    suspend fun getCodeValidate(
        email: String,
        code: String
    ): Boolean

    suspend fun postOtpSms(): Boolean

    suspend fun setPreferredLanguage(language: AppLanguageDto)

    suspend fun getUser(): UserDto

    suspend fun getReminders(): List<ReminderDto>
}
