package com.insurtech.kanguro.domain.model

enum class StatusDescriptionType(val stringValue: String) {
    Info("Info"),
    Warn("Warn"),
    Error("Error"),
    Unknown("Unknown");

    companion object {
        fun fromString(value: String): StatusDescriptionType {
            return StatusDescriptionType.values().firstOrNull { it.stringValue == value } ?: Unknown
        }
    }
}
