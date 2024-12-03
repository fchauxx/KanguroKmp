package com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LiabilitySectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LossOfUseSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.YourBelongingsUiModel
import com.insurtech.kanguro.domain.model.DeductibleItemType
import com.insurtech.kanguro.domain.model.EndorsementsDeductible
import com.insurtech.kanguro.domain.model.EndorsementsDeductibleItem
import com.insurtech.kanguro.domain.model.EndorsementsLiability
import com.insurtech.kanguro.domain.model.InvoiceInterval
import com.insurtech.kanguro.domain.model.PersonalPropertyRange
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval as InvoiceIntervalUi

fun List<EndorsementsLiability>.toUi(): LiabilitySectionModel {
    val list = this.map {
        ChipItemModel(
            id = it.id.toString(),
            value = it.value.toBigDecimal(),
            isMostPopular = it.isDefaultOption ?: false
        )
    }
    return LiabilitySectionModel(liabilityItems = list)
}

fun List<EndorsementsDeductible>.toUi(): DeductibleSectionModel {
    val list = this.map {
        ChipItemModel(
            id = it.id.toString(),
            value = it.value.toBigDecimal(),
            isMostPopular = it.isDefaultOption ?: false
        )
    }

    return DeductibleSectionModel(items = list)
}

fun List<EndorsementsDeductibleItem>.toDeductibleItemsSectionModelUi(): DeductibleItemsSectionModel {
    val medicalPaymentsPrice = this.firstOrNull {
        it.type == DeductibleItemType.MedicalPayments
    }?.value

    val hurricaneDeductible = this.firstOrNull {
        it.type == DeductibleItemType.Hurricane
    }?.value

    return DeductibleItemsSectionModel(
        medicalPaymentsPrice = medicalPaymentsPrice?.toBigDecimal(),
        hurricaneDeductible = hurricaneDeductible?.toBigDecimal()
    )
}

fun List<EndorsementsDeductibleItem>.toLossOfUseSectionModelUi(): LossOfUseSectionModel? {
    val lossOffUsePrice = this.firstOrNull {
        it.type == DeductibleItemType.LossOfUse
    }?.value

    return if (lossOffUsePrice == null) {
        null
    } else {
        LossOfUseSectionModel(price = lossOffUsePrice.toBigDecimal())
    }
}

fun PersonalPropertyRange.toUi(): YourBelongingsUiModel {
    return YourBelongingsUiModel(
        selectedValue = this.minimum.toFloat(),
        minValue = this.minimum.toFloat(),
        maxValue = this.maximum.toFloat()
    )
}

fun PolicyEndorsementPricing.toUi(): FooterSectionModel {
    return FooterSectionModel(
        buttonPrice = this.billingCyclePolicyPriceDifferenceValue.toBigDecimal(),
        totalPrice = this.billingCycleEndorsementPolicyValue.toBigDecimal(),
        invoiceInterval = when (this.billingCycle) {
            InvoiceInterval.MONTHLY -> InvoiceIntervalUi.MONTHLY
            InvoiceInterval.YEARLY -> InvoiceIntervalUi.YEARLY
            InvoiceInterval.QUARTERLY -> InvoiceIntervalUi.QUARTERLY
        }
    )
}
