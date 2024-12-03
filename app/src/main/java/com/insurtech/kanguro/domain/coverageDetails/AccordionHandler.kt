package com.insurtech.kanguro.domain.coverageDetails

import android.view.View
import androidx.lifecycle.LiveData

data class AccordionHandler(
    val isAccordionOpen: LiveData<Boolean>,
    val accordionBackground: LiveData<Int>,
    val accordionArrow: LiveData<Int>,
    val accordionClickHandler: View.OnClickListener
)
