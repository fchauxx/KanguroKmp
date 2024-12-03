package com.insurtech.kanguro.ui.scenes.billingPreferences

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.cardSelection
import com.insurtech.kanguro.domain.billingPreferences.BillingHistoryItem
import com.insurtech.kanguro.mediumTitleItem

class BillingPreferencesEpoxyController(
    private val context: Context,
    private val accordionClickHandler: (BillingHistoryItem) -> Unit
) : EpoxyController() {

    private val accordionStates = hashMapOf<BillingHistoryItem, Boolean>()

    private val billingItems = (10..20).map {
        BillingHistoryItem("Woodstock", 45.0, it.toDouble(), 18.0, "•••• 1234")
    }

    override fun buildModels() {
        val context = context

        cardSelection {
            id(0)
        }

        mediumTitleItem {
            id(1)
            text(context.getString(R.string.billing_history))
        }

        billingItems.forEach { item ->
            BillingHistoryItemViewHolder_().apply {
                id(item.hashCode())
                historyItem(item)
                accordionState(this@BillingPreferencesEpoxyController.accordionStates)
                addTo(this@BillingPreferencesEpoxyController)
            }
        }
    }
}
