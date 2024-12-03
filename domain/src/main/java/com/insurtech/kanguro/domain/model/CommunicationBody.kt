package com.insurtech.kanguro.domain.model

data class CommunicationBody(
    val message: String,
    val files: List<String>
)
