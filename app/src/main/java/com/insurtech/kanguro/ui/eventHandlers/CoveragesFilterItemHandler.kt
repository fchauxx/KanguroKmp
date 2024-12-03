package com.insurtech.kanguro.ui.eventHandlers

import com.insurtech.kanguro.domain.dashboard.CoveragesFilter

interface CoveragesFilterItemHandler {
    fun onClickFilterCoverages(item: CoveragesFilter)
}
