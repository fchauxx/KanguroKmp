package com.insurtech.kanguro.ui.eventHandlers

import com.insurtech.kanguro.databinding.LayoutCoveragesItemBinding
import com.insurtech.kanguro.domain.model.PetPolicy

interface PolicyListItemHandler {
    fun onClickPolicyItem(item: PetPolicy, view: LayoutCoveragesItemBinding)
    fun onClickAddCoverageItem()
}
