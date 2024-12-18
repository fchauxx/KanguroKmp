package com.insurtech.kanguro.shared.data

class UserRepository(
    private val kanguroUserApiService: KanguroUserApiService
) {
    suspend fun getUser(): User {
        return kanguroUserApiService.getUser()
    }
}