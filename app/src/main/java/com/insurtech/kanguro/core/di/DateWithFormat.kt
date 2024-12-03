package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.networking.di.MoshiDateAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DateWithFormat(val format: String) {

    companion object {
        var JSON_ADAPTER_FACTORY: JsonAdapter.Factory = object : JsonAdapter.Factory {
            override fun create(
                type: Type,
                annotations: Set<Annotation?>,
                moshi: Moshi
            ): JsonAdapter<*>? {
                Types.nextAnnotations(annotations, DateWithFormat::class.java) ?: return null
                val format = (annotations.firstOrNull() as? DateWithFormat)?.format ?: return null
                return MoshiDateAdapter(format)
            }
        }
    }
}
