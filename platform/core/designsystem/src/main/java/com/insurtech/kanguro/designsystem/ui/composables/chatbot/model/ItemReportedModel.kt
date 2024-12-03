package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

import com.insurtech.kanguro.designsystem.toDollarFormat
import java.math.BigDecimal

data class ItemReportedModel(
    val item: String,
    val value: BigDecimal
) {
    val formattedDollarValue: String
        get() = "U$${value.toDollarFormat()}"
}
