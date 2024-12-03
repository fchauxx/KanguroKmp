package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Reminder
import com.insurtech.kanguro.domain.model.User
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import kotlinx.coroutines.flow.Flow

interface IRefactoredUserRepository {

    suspend fun updateUserPassword(
        userUpdatePasswordBody: UserUpdatePasswordBody
    ): Result<Boolean>

    suspend fun updateUserProfile(
        userUpdateProfileBody: UserUpdateProfileBody
    ): Result<Boolean>

    suspend fun updateDeleteUserFlag(requestDeletion: Boolean): Result<Boolean>

    suspend fun getUserAccount(): Flow<Result<UserAccount>>

    suspend fun setUserAccount(userAccount: UserAccount): Result<Boolean>

    suspend fun getCodeValidate(email: String, code: String): Flow<Result<Boolean>>

    suspend fun postOtpSms(): Result<Boolean>

    suspend fun setPreferredLanguage(language: AppLanguage): Result<Unit>

    suspend fun getUser(): Result<User>

    suspend fun getReminders(): Result<List<Reminder>>
}
