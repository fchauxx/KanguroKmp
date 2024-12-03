package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.usecase.IGetPolicyUseCase
import com.insurtech.kanguro.usecase.impl.GetPolicyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetPolicyUseCase(r: GetPolicyUseCase): IGetPolicyUseCase
}
