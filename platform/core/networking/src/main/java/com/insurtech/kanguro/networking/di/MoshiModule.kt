package com.insurtech.kanguro.networking.di

import com.auth0.android.jwt.JWT
import com.insurtech.kanguro.networking.dto.ActionDto
import com.insurtech.kanguro.networking.dto.ChatbotActionTypeDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    private val chatbotaActionsSupport =
        PolymorphicJsonAdapterFactory.of(ActionDto::class.java, ActionDto.labelKey)
            .withSubtype(
                ActionDto.Text::class.java,
                ChatbotActionTypeDto.Text.toString()
            )
            .withSubtype(
                ActionDto.Date::class.java,
                ChatbotActionTypeDto.Date.toString()
            )
            .withSubtype(
                ActionDto.ScheduledItems::class.java,
                ChatbotActionTypeDto.ScheduledItems.toString()
            )
            .withSubtype(
                ActionDto.SingleChoice::class.java,
                ChatbotActionTypeDto.SingleChoice.toString()
            )
            .withSubtype(
                ActionDto.CameraCaptureVideo::class.java,
                ChatbotActionTypeDto.CameraCaptureVideo.toString()
            )
            .withSubtype(
                ActionDto.Finish::class.java,
                ChatbotActionTypeDto.Finish.toString()
            )
            .withDefaultValue(ActionDto.Unknown)

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(chatbotaActionsSupport)
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, MoshiDateAdapter().nullSafe())
        .add(JWT::class.java, MoshiJWTAdapter().nullSafe())
        .build()
}
