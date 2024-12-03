package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsAddItem

import com.insurtech.kanguro.domain.model.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval as InvoiceUi

fun InvoiceInterval.toUi(): InvoiceUi =
    when (this) {
        InvoiceInterval.MONTHLY -> InvoiceUi.MONTHLY
        InvoiceInterval.QUARTERLY -> InvoiceUi.QUARTERLY
        InvoiceInterval.YEARLY -> InvoiceUi.YEARLY
    }
