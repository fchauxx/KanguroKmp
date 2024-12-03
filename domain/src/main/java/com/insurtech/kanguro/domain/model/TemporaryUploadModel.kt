package com.insurtech.kanguro.domain.model

data class TemporaryUploadModel(
    val id: Int,
    val url: String,
    val blobType: String? = null
)
