package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import java.math.BigDecimal

data class FooterSectionModel(
    val buttonPrice: BigDecimal? = null,
    val totalPrice: BigDecimal? = null,
    val isLoading: Boolean = false,
    val invoiceInterval: InvoiceInterval? = null
)
