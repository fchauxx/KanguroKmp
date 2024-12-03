package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.data.local.PreferencesLocalDataSource
import com.insurtech.kanguro.data.source.PreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Singleton
    @Binds
    abstract fun bindPreferencesLocalDataSource(
        preferencesLocalDataSource: PreferencesLocalDataSource
    ): PreferencesDataSource
}
