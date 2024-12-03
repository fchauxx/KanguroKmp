package com.insurtech.kanguro.ui.scenes.rentersCoverageDetails

import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.date.DateUtils.MONTH_DAY_YEAR_SLASH_FORMAT
import com.insurtech.kanguro.common.date.DateUtils.getFormattedLocalDate
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalCoverageSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.HeaderSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.MainInformationSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.PaymentSectionModel
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoverageType
import com.insurtech.kanguro.domain.model.DwellingType
import com.insurtech.kanguro.domain.model.InvoiceInterval.*
import com.insurtech.kanguro.domain.model.PolicyStatus
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus as PolicyStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType as ModelDwellingType

fun RentersPolicy.toHeaderSectionModel(userName: String): HeaderSectionModel {
    return HeaderSectionModel(
        userName = userName,
        address = "${this.address.streetNumber} ${this.address.streetName}, ${this.address.city}, ${this.address.state}",
        dwellingType = when (this.dwellingType) {
            DwellingType.SingleFamily -> ModelDwellingType.SingleFamily
            DwellingType.MultiFamily -> ModelDwellingType.MultiFamily
            DwellingType.Apartment -> ModelDwellingType.Apartment
            DwellingType.Townhouse -> ModelDwellingType.Townhouse
            DwellingType.StudentHousing -> ModelDwellingType.StudentHousing
        },
        policyNumber = this.policyExternalId?.toString().orEmpty(),
        pictureUrl = "" // TODO to be implemented
    )
}

fun RentersPolicy.toMainInformationSectionModel(): MainInformationSectionModel {
    return MainInformationSectionModel(
        planSummary = PlanSummaryCardModel(
            liability = this.planSummary.liability.value.toBigDecimal(),
            deductible = this.planSummary.deductible.value.toBigDecimal(),
            personalProperty = this.planSummary.personalProperty.value.toBigDecimal(),
            lossOfUse = this.planSummary.lossOfUse.value.toBigDecimal()
        ),
        renewDate = this.endAt?.let { getFormattedLocalDate(it, MONTH_DAY_YEAR_SLASH_FORMAT) }
            .orEmpty(),
        startDate = getFormattedLocalDate(this.startAt, MONTH_DAY_YEAR_SLASH_FORMAT),
        endDate = this.endAt?.let { getFormattedLocalDate(it, MONTH_DAY_YEAR_SLASH_FORMAT) }
            .orEmpty(),
        policyStatus = when (this.status) {
            PolicyStatus.PENDING -> PolicyStatusUi.PENDING
            PolicyStatus.ACTIVE -> PolicyStatusUi.ACTIVE
            PolicyStatus.CANCELED -> PolicyStatusUi.CANCELED
            PolicyStatus.TERMINATED -> PolicyStatusUi.TERMINATED
        }
    )
}

fun RentersPolicy.toPaymentSectionModel(): PaymentSectionModel {
    return PaymentSectionModel(
        paymentValue = when (this.payment.invoiceInterval) {
            YEARLY -> this.payment.firstPayment.toBigDecimal()
            QUARTERLY -> this.payment.recurringPayment.toBigDecimal()
            MONTHLY -> this.payment.recurringPayment.toBigDecimal()
        },
        invoiceInterval = when (this.payment.invoiceInterval) {
            YEARLY -> R.string.yearly_payment
            QUARTERLY -> R.string.quarterly_payment
            MONTHLY -> R.string.monthly_payment
        },
        policyStatus = when (this.status) {
            PolicyStatus.PENDING -> PolicyStatusUi.PENDING
            PolicyStatus.ACTIVE -> PolicyStatusUi.ACTIVE
            PolicyStatus.CANCELED -> PolicyStatusUi.CANCELED
            PolicyStatus.TERMINATED -> PolicyStatusUi.TERMINATED
        }
    )
}

fun List<AdditionalCoverage>.toAdditionalCoverageSectionModel(currentAdditionalCoveragesTypes: List<AdditionalCoverageType>): AdditionalCoverageSectionModel {
    val list = this.map { it.toAdditionalCoverageItemModel(currentAdditionalCoveragesTypes) }

    return AdditionalCoverageSectionModel(additionalCoverages = list)
}

fun AdditionalCoverage.toAdditionalCoverageItemModel(currentAdditionalCoveragesTypes: List<AdditionalCoverageType>): AdditionalCoverageItemModel {
    return AdditionalCoverageItemModel(
        type = when (this.type) {
            AdditionalCoverageType.WaterSewerBackup -> AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP
            AdditionalCoverageType.FraudProtection -> AdditionalCoverageItemTypeModel.FRAUD_PROTECTION
            AdditionalCoverageType.ReplacementCost -> AdditionalCoverageItemTypeModel.REPLACEMENT_COST
        },
        coverageLimit = this.coverageLimit?.toBigDecimal(),
        deductible = this.deductibleLimit?.toBigDecimal(),
        intervalTotal = this.intervalTotal.toBigDecimal(),
        isActive = currentAdditionalCoveragesTypes.contains(this.type)
    )
}
