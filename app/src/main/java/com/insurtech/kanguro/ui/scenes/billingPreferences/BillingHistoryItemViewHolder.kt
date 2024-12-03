package com.insurtech.kanguro.ui.scenes.billingPreferences

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutBillingHistoryComponentBinding
import com.insurtech.kanguro.domain.billingPreferences.BillingHistoryItem

@EpoxyModelClass(layout = R.layout.layout_billing_history_component)
abstract class BillingHistoryItemViewHolder : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var historyItem: BillingHistoryItem? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var accordionState: MutableMap<BillingHistoryItem, Boolean>? = null

    private var isOpen = false

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? LayoutBillingHistoryComponentBinding)?.let { binding ->
            binding.billingItem = historyItem!!
            isOpen = accordionState?.get(historyItem!!) ?: false
            binding.isAccordionVisible = isOpen
            // binding.rootLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            binding.cellHeader.setOnClickListener {
                isOpen = !isOpen
                accordionState?.put(historyItem!!, isOpen)
                binding.isAccordionVisible = isOpen
            }
        }
    }
}
