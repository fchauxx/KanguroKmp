package com.insurtech.kanguro.domain.coverageDetails

import com.insurtech.kanguro.domain.model.PolicyDocument

interface GoodBoyHandler {
    fun onWhatsCoveredPressed()
    fun onPolicyDocsPressed()
    fun onPolicyDocPressed(document: PolicyDocument)
}
