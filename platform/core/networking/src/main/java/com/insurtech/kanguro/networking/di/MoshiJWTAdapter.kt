package com.insurtech.kanguro.networking.di

import com.auth0.android.jwt.JWT
import com.squareup.moshi.*

class MoshiJWTAdapter : JsonAdapter<JWT>() {

    @FromJson
    override fun fromJson(reader: JsonReader): JWT? {
        return try {
            JWT(reader.nextString())
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: JWT?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}
