package com.insurtech.kanguro.shared.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KanguroUserApiService(private val client:HttpClient) {
    suspend fun getUser(): User {
        return client.get("/api/user").body<User>()
    }
}