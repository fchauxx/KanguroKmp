package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalParties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.databinding.FragmentRentersEditAdditionalPartiesBinding
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.RentersEditAdditionalPartiesScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.model.RentersEditAdditionalPartiesEvent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.rentersEditAdditionalParties.model.RentersEditAdditionalPartiesModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersEditAdditionalPartiesFragment :
    KanguroBottomSheetFragment<FragmentRentersEditAdditionalPartiesBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersEditAdditionalParties

    override val viewModel: RentersEditAdditionalPartiesViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersEditAdditionalPartiesBinding =
        FragmentRentersEditAdditionalPartiesBinding.inflate(inflater)

    override val isDraggable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                RentersEditAdditionalPartiesFragment(
                    viewModel = viewModel,
                    onEvent = ::handleEvent
                )
            }
        }
    }

    private fun handleEvent(event: RentersEditAdditionalPartiesEvent) {
        when (event) {
            is RentersEditAdditionalPartiesEvent.OnClosePressed -> onClosePressed()

            is RentersEditAdditionalPartiesEvent.OnDeletePressed -> viewModel.handleOnDeleteAdditionalPartyPressed(event.partyId)

            is RentersEditAdditionalPartiesEvent.OnDeleteConfirmationPressed -> viewModel.handleOnDeleteAdditionalPartyConfirmed()

            is RentersEditAdditionalPartiesEvent.OnSubmitEdition -> {} /* TODO: handle OnSubmitEdition*/

            is RentersEditAdditionalPartiesEvent.OnAddPartyPressed -> {} /* TODO: handle OnAddPartyPressed*/

            is RentersEditAdditionalPartiesEvent.OnEditPressed -> {} /* TODO: handle OnEditPressed*/

            is RentersEditAdditionalPartiesEvent.OnTryAgainPressed -> viewModel.fetchData()
        }
    }

    private fun onClosePressed() {
        findNavController().popBackStack()
    }
}

@Composable
private fun RentersEditAdditionalPartiesFragment(
    viewModel: RentersEditAdditionalPartiesViewModel,
    onEvent: (RentersEditAdditionalPartiesEvent) -> Unit
) {
    val uiState: UiState<RentersEditAdditionalPartiesModel> by viewModel.uiState.collectAsState()

    RentersEditAdditionalPartiesScreenContent(
        parties = AdditionalPartiesSectionModel(
            (uiState as? UiState.Success)?.data?.partyItemList.orEmpty()
        ),
        onEvent = onEvent,
        isLoading = uiState is UiState.Loading,
        isError = uiState is UiState.Error
    )
}

@Preview
@Composable
private fun RentersEditAdditionalPartiesFragmentPreview() {
    Surface {
        RentersEditAdditionalPartiesScreenContent(
            onEvent = {},
            parties = AdditionalPartiesSectionModel(
                additionalParties = listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    ),
                    PartyItemModel(
                        id = "2",
                        name = "Emily Walker Thompson",
                        relation = PartyItemModelRelation.Child
                    ),
                    PartyItemModel(
                        id = "3",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    ),
                    PartyItemModel(
                        id = "4",
                        name = "Olivia Roberts",
                        relation = PartyItemModelRelation.Roommate
                    ),
                    PartyItemModel(
                        id = "5",
                        name = "Matthew Anderson",
                        relation = PartyItemModelRelation.PropertyManager
                    )

                )
            ),
            isLoading = false,
            isError = false
        )
    }
}

@Preview
@Composable
fun EditAdditionalRemindersErrorPreview() {
    Surface {
        RentersEditAdditionalPartiesScreenContent(
            onEvent = {},
            parties = AdditionalPartiesSectionModel(
                additionalParties = listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    )

                )
            ),
            isLoading = false,
            isError = true
        )
    }
}

@Preview
@Composable
fun EditAdditionalRemindersLoadingPreview() {
    Surface {
        RentersEditAdditionalPartiesScreenContent(
            onEvent = {},
            parties = AdditionalPartiesSectionModel(
                additionalParties = listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    )

                )
            ),
            isLoading = true,
            isError = false
        )
    }
}
