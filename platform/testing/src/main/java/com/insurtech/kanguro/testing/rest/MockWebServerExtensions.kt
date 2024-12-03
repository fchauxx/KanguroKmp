package com.insurtech.kanguro.testing.rest

import com.auth0.android.jwt.JWT
import com.insurtech.kanguro.networking.RetrofitBuilder
import com.insurtech.kanguro.networking.di.MoshiDateAdapter
import com.insurtech.kanguro.networking.di.MoshiJWTAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import java.util.Date

inline fun <reified T> MockWebServer.wireRestApi(): T {
    val url = url("/").toString()

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, MoshiDateAdapter().nullSafe())
        .add(JWT::class.java, MoshiJWTAdapter().nullSafe())
        .build()

    return RetrofitBuilder(url.toHttpUrl(), client, moshi)
        .create(T::class.java)
}
