package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.AppLanguageMapper.mapAppLanguageToAppLanguageDto
import com.insurtech.kanguro.data.mapper.ReminderMapper
import com.insurtech.kanguro.data.mapper.UserMapper
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.data.source.UserDataSource
import com.insurtech.kanguro.domain.model.Reminder
import com.insurtech.kanguro.domain.model.User
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import com.insurtech.kanguro.networking.dto.ReminderDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefactoredUserRepository @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val petDataSource: PetDataSource
) : IRefactoredUserRepository {

    override suspend fun updateUserPassword(userUpdatePasswordBody: UserUpdatePasswordBody): Result<Boolean> =
        Result.Success(
            userRemoteDataSource.updateUserPassword(
                UserMapper.mapUserUpdatePasswordBodyToUserUpdatePasswordBodyDto(
                    userUpdatePasswordBody
                )
            )
        )

    override suspend fun updateUserProfile(userUpdateProfileBody: UserUpdateProfileBody): Result<Boolean> =
        Result.Success(
            userRemoteDataSource.updateUserProfile(
                UserMapper.mapUserUpdateProfileBodyToUserUpdateProfileBodyDto(userUpdateProfileBody)
            )
        )

    override suspend fun updateDeleteUserFlag(requestDeletion: Boolean): Result<Boolean> =
        Result.Success(
            userRemoteDataSource.updateDeleteUserFlag(requestDeletion)
        )

    override suspend fun getUserAccount(): Flow<Result<UserAccount>> = flow {
        try {
            val userAccount = userRemoteDataSource.getUserAccount()
            emit(Result.Success(userAccount))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun setUserAccount(userAccount: UserAccount): Result<Boolean> =
        Result.Success(
            userRemoteDataSource.setUserAccount(
                UserMapper.mapUserAccountToUserAccountBodyDto(userAccount)
            )
        )

    override suspend fun getCodeValidate(email: String, code: String): Flow<Result<Boolean>> =
        flow {
            try {
                val status = userRemoteDataSource.getCodeValidate(email, code)
                emit(Result.Success(status))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun postOtpSms(): Result<Boolean> =
        Result.Success(userRemoteDataSource.postOtpSms())

    override suspend fun setPreferredLanguage(language: AppLanguage): Result<Unit> {
        return try {
            userRemoteDataSource.setPreferredLanguage(mapAppLanguageToAppLanguageDto(language))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUser(): Result<User> {
        try {
            val user = UserMapper.mapUserDtoToUser(
                userRemoteDataSource.getUser()
            ) ?: return Result.Error(NullPointerException())

            return Result.Success(user)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getReminders(): Result<List<Reminder>> {
        return try {
            val userReminders = userRemoteDataSource.getReminders()

            val reminders = userReminders.mapNotNull { reminderDto ->
                getReminder(reminderDto)
            }

            Result.Success(reminders)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun getReminder(reminderDto: ReminderDto): Reminder? {
        val petId = reminderDto.petId
        return if (petId != null) {
            ReminderMapper.mapReminderDtoToReminder(reminderDto, petDataSource.getPetDetails(petId))
        } else {
            null
        }
    }
}
