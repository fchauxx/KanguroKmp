package com.insurtech.kanguro.ui.scenes.coverages

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.composableInterop
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AdvertiserCardCarousel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.AdvertiserCardModels
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList.LiveVetPetsCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.theme.spacingXs
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.ui.epoxy.moreActionsSection
import com.insurtech.kanguro.ui.eventHandlers.AdvertisingHandler
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import kotlinx.coroutines.flow.MutableStateFlow

class CoveragesEpoxyController(
    private val context: Context,
    private val advertisingHandler: AdvertisingHandler,
    private val moreActionsListItemHandler: MoreActionsListItemHandler,
    private val onClickAddPetCard: () -> Unit,
    private val onClickPetCard: (String) -> Unit
) : EpoxyController() {

    private var coverageList = MutableStateFlow<List<PetsCoverageSummaryCardModel>>(emptyList())

    var shouldShowLiveVet: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    fun setCoveragesList(coverages: List<PetsCoverageSummaryCardModel>) {
        coverageList.value = coverages
        requestModelBuild()
    }

    override fun buildModels() {
        val moreActionsList = mutableListOf(
            Action.FileClaim,
            Action.TrackClaims,
            Action.DirectPayYourVet,
            Action.GetQuote,
            Action.PaymentSettings,
            Action.FindVet,
            Action.FrequentQuestions
        ).apply {
            if (shouldShowLiveVet) add(0, Action.LiveVet)
        }

        composableInterop("petsCarousel") {
            val coverages = coverageList.collectAsState()

            LiveVetPetsCoveragesListComponent(
                modifier = Modifier.padding(top = spacingXs),
                coverages = coverages.value,
                onCoveragePressed = { onClickPetCard(it) },
                onAddPetPressed = { onClickAddPetCard() },
                onLiveVetPressed = { moreActionsListItemHandler.onClickMoreActionsItem(Action.LiveVet) }
            )
        }

        composableInterop("advertiserCarousel") {
            AdvertiserCardCarousel(
                modifier = Modifier.padding(top = spacingXs),
                defaultOnClickCard = {
                    this@CoveragesEpoxyController.advertisingHandler.onClickAdvertising(
                        it
                    )
                },
                cardList = mutableStateListOf(
                    AdvertiserCardModels.Roam,
                    AdvertiserCardModels.MissingPets
                )
            )
        }

        moreActionsSection(context = context, moreActionsList = moreActionsList) {
            this@CoveragesEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(it)
        }
    }
}
