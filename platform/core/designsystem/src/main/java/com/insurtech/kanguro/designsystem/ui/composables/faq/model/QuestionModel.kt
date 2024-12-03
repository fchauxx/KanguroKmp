package com.insurtech.kanguro.designsystem.ui.composables.faq.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class QuestionModel(
    val question: String,
    val answer: String
) {
    var isExpanded by mutableStateOf(false)
}
