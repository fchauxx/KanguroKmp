package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.JavierTitleXClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.OutlinedButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.model.RentersEditAdditionalPartiesEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.sections.AdditionalPartiesList
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems.DeleteItemAlertDialog
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun RentersEditAdditionalPartiesScreenContent(
    parties: AdditionalPartiesSectionModel,
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (RentersEditAdditionalPartiesEvent) -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Column {
        JavierTitleXClose(title = stringResource(R.string.here_s_the_list_of_your_additional_parties)) {
            onEvent(RentersEditAdditionalPartiesEvent.OnClosePressed)
        }
        ParentComponent(
            onEvent = onEvent,
            isShowable = !isLoading && !isError,
            parties = parties.additionalParties
        )

        LoadingErrorStates(
            isLoading = isLoading,
            isError = isError,
            onEvent = onEvent
        )

        if (openAlertDialog) {
            DeleteItemAlertDialog(
                onClosePressed = { openAlertDialog = false },
                onCancelPressed = { openAlertDialog = false }
            ) {
                openAlertDialog = false
                onEvent(
                    RentersEditAdditionalPartiesEvent.OnDeleteConfirmationPressed
                )
            }
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    onEvent: (RentersEditAdditionalPartiesEvent) -> Unit
) {
    Column(
        modifier.padding(start = 32.dp, end = 32.dp, top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            text = R.string.add_additional_party
        ) { onEvent(RentersEditAdditionalPartiesEvent.OnAddPartyPressed) }

        Spacer(modifier = Modifier.height(8.dp))

        KanguroButton(
            enabled = true,
            text = stringResource(id = R.string.submit)
        ) { onEvent(RentersEditAdditionalPartiesEvent.OnSubmitEdition) }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
private fun RentersEditAdditionalPartiesScreenContentPreview() {
    Surface {
        RentersEditAdditionalPartiesScreenContent(
            parties = AdditionalPartiesSectionModel(
                additionalParties = listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    ),
                    PartyItemModel(
                        id = "1",
                        name = "Emily Walker Thompson",
                        relation = PartyItemModelRelation.Child
                    ),
                    PartyItemModel(
                        id = "1",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    ),
                    PartyItemModel(
                        id = "1",
                        name = "Olivia Roberts",
                        relation = PartyItemModelRelation.Roommate
                    ),
                    PartyItemModel(
                        id = "1",
                        name = "Matthew Anderson",
                        relation = PartyItemModelRelation.PropertyManager
                    ),
                    PartyItemModel(
                        id = "1",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    )
                )
            ),
            onEvent = {},
            isLoading = false,
            isError = false
        )
    }
}

@Composable
fun ParentComponent(
    onEvent: (RentersEditAdditionalPartiesEvent) -> Unit,
    isShowable: Boolean,
    parties: List<PartyItemModel>
) {
    if (isShowable) {
        Column {
            AdditionalPartiesList(
                parties = parties,
                modifier = Modifier.weight(1f),
                onDeletePressed = { onEvent(RentersEditAdditionalPartiesEvent.OnDeletePressed(it)) },
                onEditPressed = { onEvent(RentersEditAdditionalPartiesEvent.OnEditPressed(it)) }
            )

            Footer(
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun LoadingErrorStates(
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (RentersEditAdditionalPartiesEvent) -> Unit
) {
    if (isLoading) {
        Loader()
    } else if (isError) {
        Error {
            onEvent(RentersEditAdditionalPartiesEvent.OnTryAgainPressed)
        }
    }
}

@Preview
@Composable
fun FooterPreview() {
    Surface {
        Footer {}
    }
}

@Composable
fun Loader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (loader) = createRefs()

        ScreenLoader(
            color = NeutralBackground,
            modifier = Modifier
                .size(width = 87.dp, height = 84.dp)
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.45f)
                    linkTo(start = parent.start, end = parent.end)
                }

        )
    }
}

@Composable
fun Error(
    onTryAgainPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
    ) {
        val (loader) = createRefs()

        ErrorComponent(
            onTryAgainPressed = onTryAgainPressed,
            modifier = Modifier
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}
