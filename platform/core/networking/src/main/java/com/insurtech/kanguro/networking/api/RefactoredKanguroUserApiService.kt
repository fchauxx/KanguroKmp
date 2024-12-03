package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ReminderDto
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserAccountDto
import com.insurtech.kanguro.networking.dto.UserDto
import com.insurtech.kanguro.networking.dto.UserUpdateLanguagePreferenceBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RefactoredKanguroUserApiService {

    @PUT("api/user/updatePassword")
    suspend fun putUpdatePassword(
        @Body body: UserUpdatePasswordBodyDto
    ): Response<Unit>

    @PUT("api/user/updateUserProfile")
    suspend fun putUpdateUserProfile(
        @Body userUpdateProfileBodyDto: UserUpdateProfileBodyDto
    ): Response<Unit>

    @PUT("api/user/updateDeleteUserDataFlag")
    suspend fun putDeleteUserFlag(@Body requestDeletion: Boolean): Response<Unit>

    @GET("/api/user/UserAccount")
    suspend fun getUserAccount(): UserAccountDto

    @PUT("/api/user/UserAccount")
    suspend fun putUserAccount(@Body body: UserAccountBodyDto): Response<Unit>

    @PUT("api/user/preferredLanguage")
    suspend fun putUserPreferredLanguage(
        @Body body: UserUpdateLanguagePreferenceBodyDto
    ): Response<Unit>

    @GET("api/user/otpsms/validate")
    suspend fun getCodeValidate(
        @Query("Email") email: String,
        @Query("Code") code: String
    ): Boolean

    @POST("api/user/otpsms/request")
    suspend fun postOtpSms(): Response<Unit>

    @GET("/api/user")
    suspend fun getUser(): UserDto

    @GET("api/user/reminders")
    suspend fun getReminders(): List<ReminderDto>
}
