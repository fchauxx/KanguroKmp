package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

data class SelectedFileUi(
    val id: Int,
    val fileName: String? = null,
    val fileUrl: String? = null,
    val type: SelectedFileType? = null
)
