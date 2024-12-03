package com.insurtech.kanguro.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.core.api.KanguroChatbotApiService
import com.insurtech.kanguro.core.api.KanguroClaimsApiService
import com.insurtech.kanguro.core.api.KanguroPetsApiService
import com.insurtech.kanguro.core.api.KanguroPolicyApiService
import com.insurtech.kanguro.core.api.KanguroUserApiService
import com.insurtech.kanguro.core.interceptors.BroadcastUnauthorizedInterceptor
import com.insurtech.kanguro.core.interceptors.HeaderInterceptor
import com.insurtech.kanguro.core.interceptors.LanguageInterceptor
import com.insurtech.kanguro.core.interceptors.RefreshTokenInterceptor
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.data.remote.LoginRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.LoginRepository
import com.insurtech.kanguro.networking.RetrofitBuilder
import com.insurtech.kanguro.networking.api.GooglePlacesApiService
import com.insurtech.kanguro.networking.api.KanguroApiVersionService
import com.insurtech.kanguro.networking.api.KanguroCharityApiService
import com.insurtech.kanguro.networking.api.KanguroChatbotVersionedApiService
import com.insurtech.kanguro.networking.api.KanguroContactInformationApiService
import com.insurtech.kanguro.networking.api.KanguroExternalLinksApiService
import com.insurtech.kanguro.networking.api.KanguroLoginService
import com.insurtech.kanguro.networking.api.KanguroRentersPolicyApiService
import com.insurtech.kanguro.networking.api.KanguroRentersPolicyEndorsementApiService
import com.insurtech.kanguro.networking.api.KanguroRentersPricingApiService
import com.insurtech.kanguro.networking.api.KanguroRentersScheduledItemsApiService
import com.insurtech.kanguro.networking.api.KanguroTemporaryFileApiService
import com.insurtech.kanguro.networking.api.KanguroTermApiService
import com.insurtech.kanguro.networking.api.KanguroVetAdvicesApiService
import com.insurtech.kanguro.networking.api.KanguroVeterinarianApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroBanksApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroClaimsApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroCloudDocumentService
import com.insurtech.kanguro.networking.api.RefactoredKanguroPetApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroPolicyApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroUserApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providePetsApiService(retrofit: Retrofit): KanguroPetsApiService =
        retrofit.create(KanguroPetsApiService::class.java)

    @Provides
    fun provideChatbotApiService(retrofit: Retrofit): KanguroChatbotApiService =
        retrofit.create(KanguroChatbotApiService::class.java)

    @Provides
    fun provideUserApiService(retrofit: Retrofit): KanguroUserApiService =
        retrofit.create(KanguroUserApiService::class.java)

    @Provides
    fun providePolicyApiService(retrofit: Retrofit): KanguroPolicyApiService =
        retrofit.create(KanguroPolicyApiService::class.java)

    @Provides
    fun provideClaimsApiService(retrofit: Retrofit): KanguroClaimsApiService =
        retrofit.create(KanguroClaimsApiService::class.java)

    @Provides
    fun provideTermApiService(retrofit: Retrofit): KanguroTermApiService =
        retrofit.create(KanguroTermApiService::class.java)

    @Provides
    fun provideVetAdvicesApiService(retrofit: Retrofit): KanguroVetAdvicesApiService =
        retrofit.create(KanguroVetAdvicesApiService::class.java)

    @Provides
    fun provideBackendVersionApiService(retrofit: Retrofit): KanguroApiVersionService =
        retrofit.create(KanguroApiVersionService::class.java)

    @Provides
    fun provideRefactoredKanguroUserApiService(retrofit: Retrofit): RefactoredKanguroUserApiService =
        retrofit.create(RefactoredKanguroUserApiService::class.java)

    @Provides
    fun provideRefactoredKanguroClaimsApiService(retrofit: Retrofit): RefactoredKanguroClaimsApiService =
        retrofit.create(RefactoredKanguroClaimsApiService::class.java)

    @Provides
    fun provideRefactoredKanguroCloudDocumentService(retrofit: Retrofit): RefactoredKanguroCloudDocumentService =
        retrofit.create(RefactoredKanguroCloudDocumentService::class.java)

    @Provides
    fun provideRefactoredKanguroPolicyApiService(retrofit: Retrofit): RefactoredKanguroPolicyApiService =
        retrofit.create(RefactoredKanguroPolicyApiService::class.java)

    @Provides
    fun provideRefactoredKanguroPetApiService(retrofit: Retrofit): RefactoredKanguroPetApiService =
        retrofit.create(RefactoredKanguroPetApiService::class.java)

    @Provides
    fun provideGooglePlacesApiService(@RetrofitPlacesSDK retrofit: Retrofit): GooglePlacesApiService =
        retrofit.create(GooglePlacesApiService::class.java)

    @Provides
    fun provideKanguroCharityApiService(retrofit: Retrofit): KanguroCharityApiService =
        retrofit.create(KanguroCharityApiService::class.java)

    @Provides
    fun provideKanguroChatbotVersionedApiService(retrofit: Retrofit): KanguroChatbotVersionedApiService =
        retrofit.create(KanguroChatbotVersionedApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        provideBaseRetrofit(BuildConfig.BASE_API_URL, okHttpClient, moshi)

    @Provides
    @Singleton
    fun provideKanguroLoginService(retrofit: Retrofit): KanguroLoginService =
        retrofit.create(KanguroLoginService::class.java)

    @Provides
    @Singleton
    fun provideKanguroRentersPolicyApiService(retrofit: Retrofit): KanguroRentersPolicyApiService =
        retrofit.create(KanguroRentersPolicyApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroRentersScheduledItemsApiService(retrofit: Retrofit): KanguroRentersScheduledItemsApiService =
        retrofit.create(KanguroRentersScheduledItemsApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroTemporaryFileApiService(retrofit: Retrofit): KanguroTemporaryFileApiService =
        retrofit.create(KanguroTemporaryFileApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroVeterinarianApiService(retrofit: Retrofit): KanguroVeterinarianApiService =
        retrofit.create(KanguroVeterinarianApiService::class.java)

    @Provides
    @Singleton
    fun provideRefactoredKanguroBanksApiService(retrofit: Retrofit): RefactoredKanguroBanksApiService =
        retrofit.create(RefactoredKanguroBanksApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroRentersPricingApiService(retrofit: Retrofit): KanguroRentersPricingApiService =
        retrofit.create(KanguroRentersPricingApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroRentersPolicyEndorsementApiService(retrofit: Retrofit): KanguroRentersPolicyEndorsementApiService =
        retrofit.create(KanguroRentersPolicyEndorsementApiService::class.java)

    @Provides
    @Singleton
    fun provideContactInformationApiService(retrofit: Retrofit): KanguroContactInformationApiService =
        retrofit.create(KanguroContactInformationApiService::class.java)

    @Provides
    @Singleton
    fun provideKanguroExternalLinksApiService(retrofit: Retrofit): KanguroExternalLinksApiService =
        retrofit.create(KanguroExternalLinksApiService::class.java)

    @Provides
    @Singleton
    @RetrofitPlacesSDK
    fun provideRetrofitPlacesSDK(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        provideBaseRetrofit(BASE_PLACES_SDK_URL, okHttpClient, moshi)

    private fun provideBaseRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        RetrofitBuilder(
            url = baseUrl.toHttpUrl(),
            httpClient = okHttpClient,
            moshi = moshi
        )

    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor,
        languageInterceptor: LanguageInterceptor,
        broadcastUnauthorizedInterceptor: BroadcastUnauthorizedInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .readTimeout(45, TimeUnit.SECONDS)
        .connectTimeout(45, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(refreshTokenInterceptor)
        .addInterceptor(languageInterceptor)
        .addInterceptor(broadcastUnauthorizedInterceptor)
        .addInterceptor(chuckerInterceptor)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
        }.build()

    @Provides
    fun provideRefreshTokenInterceptor(
        headerInterceptor: HeaderInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        sessionManager: ISessionManager,
        moshi: Moshi
    ): RefreshTokenInterceptor {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(chuckerInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                    )
                }
            }.build()

        val retrofit = provideRetrofit(client, moshi)
        val loginRepository = LoginRepository(
            LoginRemoteDataSource(retrofit.create())
        )
        return RefreshTokenInterceptor(sessionManager, loginRepository)
    }

    @Provides
    fun provideHeaderInterceptor() = HeaderInterceptor(BuildConfig.KANGURO_API_KEY)

    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitPlacesSDK

    private const val BASE_PLACES_SDK_URL =
        "https://maps.googleapis.com/maps/"
}
