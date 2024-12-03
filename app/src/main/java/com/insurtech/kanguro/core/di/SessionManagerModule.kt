package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.session.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionManagerModule {

    @Binds
    @Singleton
    abstract fun provideSessionManager(sessionManager: SessionManager): ISessionManager
}
