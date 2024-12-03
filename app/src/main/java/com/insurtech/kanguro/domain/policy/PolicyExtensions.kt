package com.insurtech.kanguro.domain.policy

import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.PetType
import com.insurtech.kanguro.common.enums.PolicyInvoiceInterval
import com.insurtech.kanguro.domain.model.PetPolicy

fun PetPolicy.getPlanName(): Int? = when (pet?.type) {
    PetType.Cat -> R.string.gato_sano
    PetType.Dog -> R.string.perro_bueno
    null -> null
}

fun PetPolicy.getPlanImage(): Int? = when (pet?.type) {
    PetType.Cat -> R.drawable.ic_gato_sano
    PetType.Dog -> R.drawable.ic_perro_bueno
    null -> null
}

val PetPolicy.invoiceCost: Float?
    get() = when (this.payment?.invoiceInterval) {
        PolicyInvoiceInterval.MONTHLY,
        PolicyInvoiceInterval.QUARTERLY -> payment?.recurringPayment

        PolicyInvoiceInterval.YEARLY -> payment?.firstPayment
        else -> null
    }
