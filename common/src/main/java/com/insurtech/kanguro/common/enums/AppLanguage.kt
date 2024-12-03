package com.insurtech.kanguro.common.enums

enum class AppLanguage(val language: String) {
    English("en"), Spanish("es");

    companion object {

        fun toLanguage(enum: AppLanguage) = when (enum) {
            Spanish -> "es"
            else -> "en"
        }

        fun fromLanguage(language: String) = when (language) {
            "es" -> Spanish
            else -> English
        }
    }
}
