package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.networking.api.UploadFileApiService
import com.insurtech.kanguro.networking.api.UploadFileApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UploadFileModule {

    @Singleton
    @Binds
    abstract fun bindUploadFileRepository(r: UploadFileApiServiceImpl): UploadFileApiService
}
