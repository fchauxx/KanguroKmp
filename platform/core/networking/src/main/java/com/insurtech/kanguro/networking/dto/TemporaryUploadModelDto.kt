package com.insurtech.kanguro.networking.dto

data class TemporaryUploadModelDto(
    val id: Int,
    val url: String,
    val blobType: String? = null
)
