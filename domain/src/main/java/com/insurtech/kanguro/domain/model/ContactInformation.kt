package com.insurtech.kanguro.domain.model

data class ContactInformation(
    val type: ContactInformationType,
    val action: String?,
    val data: ContactInformationData
)
