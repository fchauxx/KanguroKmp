package com.insurtech.kanguro.testing.extensions

import com.insurtech.kanguro.networking.di.MoshiDateAdapter
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

inline fun <reified T> String.mapJsonToModel(): T? {
    val jsonStr = FileSystemSupport.loadFile(this)

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, MoshiDateAdapter().nullSafe())
        .build()
    val jsonAdapter = moshi.adapter(T::class.java)

    return jsonAdapter.fromJson(jsonStr)
}

inline fun <reified T> String.mapJsonToListOfModels(): List<T>? {
    val jsonStr = FileSystemSupport.loadFile(this)

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(
        List::class.java,
        T::class.java
    )
    val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)

    return jsonAdapter.fromJson(jsonStr)
}
