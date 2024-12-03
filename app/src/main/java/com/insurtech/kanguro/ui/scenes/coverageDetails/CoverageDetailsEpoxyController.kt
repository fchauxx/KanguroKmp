package com.insurtech.kanguro.ui.scenes.coverageDetails

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.domain.coverageDetails.GoodBoyHandler
import com.insurtech.kanguro.domain.coverageDetails.PaymentHandler
import com.insurtech.kanguro.domain.coverageDetails.PreventViewHandler
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.paymentCard
import com.insurtech.kanguro.preventViewCard
import com.insurtech.kanguro.ui.epoxy.moreActionsSection
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import com.insurtech.kanguro.ui.scenes.coverageDetails.viewHolder.GoodBoyItemViewHolder_

class CoverageDetailsEpoxyController(
    private val context: Context,
    private val moreActionsListItemHandler: MoreActionsListItemHandler,
    private val goodBoyHandler: GoodBoyHandler,
    private val paymentHandler: PaymentHandler,
    private val preventViewHandler: PreventViewHandler,
    private val item: PetPolicy
) : EpoxyController() {

    private val moreActionsList = listOf(
        Action.FileClaim,
        Action.TrackClaims, /*Action.FrequentQuestions, Action.TalkToJavier*/
        Action.DirectPayYourVet
    )

    var documentList: List<PolicyDocument>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var downloadedDocument: PolicyDocument? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var summaryOpen = false
    var documentsOpen = false

    override fun buildModels() {
        GoodBoyItemViewHolder_(
            item,
            goodBoyHandler,
            {
                summaryOpen = it
                requestModelBuild()
            },
            {
                documentsOpen = it
                requestModelBuild()
            }
        ).apply {
            id(0)
            summaryOpen(this@CoverageDetailsEpoxyController.summaryOpen)
            documentsOpen(this@CoverageDetailsEpoxyController.documentsOpen)
            documentsList(this@CoverageDetailsEpoxyController.documentList)
            documentBeingDownloaded(this@CoverageDetailsEpoxyController.downloadedDocument)
            addTo(this@CoverageDetailsEpoxyController)
        }

        if (item.preventive == true) {
            preventViewCard {
                id(1)
                policy(this@CoverageDetailsEpoxyController.item)
                onClickAction { _ ->
                    this@CoverageDetailsEpoxyController.preventViewHandler.preventViewWhatsCoveredPressed()
                }
            }
        }

        paymentCard {
            id("CurrentPayment${this@CoverageDetailsEpoxyController.item.hashCode()}")
            policy(this@CoverageDetailsEpoxyController.item)
            onClickAction(this@CoverageDetailsEpoxyController.paymentHandler.navigateToBillingPreferences)
        }

        moreActionsSection(
            context = context,
            background = R.color.neutral_background,
            moreActionsList = moreActionsList
        ) {
            this@CoverageDetailsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(it)
        }
    }
}
