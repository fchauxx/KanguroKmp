package com.insurtech.kanguro.ui.scenes.paymentSettings

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.insurtech.kanguro.*
import com.insurtech.kanguro.domain.model.PetPolicy

class PaymentSettingsEpoxyController(
    private val context: Context,
    private val onPaymentMethodPressed: () -> Unit,
    private val onReimbursementPressed: () -> Unit
) : EpoxyController() {

    var policyList: List<PetPolicy> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        val context = context
        mediumTitleItem {
            id(R.string.preferences)
            text(context.getString(R.string.preferences))
        }

        itemArrowRight {
            id(0)
            text(context.getString(R.string.paymentMethod))
            onClickAction { _ -> this@PaymentSettingsEpoxyController.onPaymentMethodPressed() }
        }

        itemArrowRight {
            id(1)
            text(context.getString(R.string.reimbursement_account))
            onClickAction { _ -> this@PaymentSettingsEpoxyController.onReimbursementPressed() }
        }

        if (policyList.isNotEmpty()) {
            mediumTitleItem {
                id(R.string.payments)
                text(context.getString(R.string.payments))
            }
        }

        policyList.forEach { payment ->
            paymentSettingsHistoryCard {
                id("Payment${payment.hashCode()}")
                policy(payment)
            }
        }
    }
}
