package com.insurtech.kanguro.core.repository.user

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.api.KanguroPetsApiService
import com.insurtech.kanguro.core.api.KanguroUserApiService
import com.insurtech.kanguro.core.api.bodies.*
import com.insurtech.kanguro.core.api.responses.UserResponse
import com.insurtech.kanguro.core.repository.RepoUtils
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.toReminder
import com.insurtech.kanguro.domain.login.KeycloakResponse
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.extensions.convert
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: KanguroUserApiService,
    private val petsApiService: KanguroPetsApiService
) : BaseRepository(), IUserRepository {

    override suspend fun resetPassword(email: String): CompletableResponse<ErrorDto> {
        return getSafeNetworkResponse {
            userService.resetPassword(ResetPasswordBody(email))
        }
    }

    override suspend fun getReminders(): NetworkResponse<List<Reminder>, ErrorDto> {
        return getSafeNetworkResponse {
            val remindersResponse = userService.getReminders()

            val requiredPets =
                (remindersResponse as? NetworkResponse.Success)?.body?.mapNotNull { it.petId }
                    ?.toSet()

            val petDetails = RepoUtils.getPetDetails(petsApiService, requiredPets ?: emptySet())

            remindersResponse.convert {
                it.mapNotNull { reminder ->
                    val pet = petDetails[reminder.petId] ?: return@mapNotNull null
                    reminder.toReminder(pet)
                }
            }
        }
    }

    override suspend fun setPreferredLanguage(language: AppLanguage): CompletableResponse<ErrorDto> {
        return getSafeNetworkResponse {
            userService.putUserPreferredLanguage(UserUpdateLanguagePreferenceBody(language))
        }
    }

    override suspend fun keycloak(): NetworkResponse<KeycloakResponse, ErrorDto> {
        return getSafeNetworkResponse { userService.keycloak() }
    }

    override suspend fun putUserFirebaseToken(token: TokenBody): CompletableResponse<ErrorDto> {
        return getSafeNetworkResponse {
            userService.putUserFirebaseToken(token)
        }
    }

    override suspend fun getHasAccessBlocked(
        userId: String
    ): NetworkResponse<Boolean, ErrorDto> {
        return getSafeNetworkResponse { userService.getHasAccessBlocked(userId) }
    }

    override suspend fun getUser(): NetworkResponse<UserResponse, ErrorDto> {
        return getSafeNetworkResponse {
            userService.getUser()
        }
    }
}
