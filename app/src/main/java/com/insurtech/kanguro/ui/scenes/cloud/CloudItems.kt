package com.insurtech.kanguro.ui.scenes.cloud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CloudItems(
    modifier: Modifier = Modifier,
    cloudPets: List<CloudPet>,
    cloudRenters: List<CloudRenters>,
    isRefreshing: Boolean,
    onPetSelected: (CloudPet) -> Unit,
    onRentersSelected: (CloudRenters) -> Unit,
    onPullToRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = onPullToRefresh
    )

    Box(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        if (isRefreshing) {
            ScreenLoader(
                modifier = Modifier.fillMaxSize(),
                color = White
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 32.dp)
            ) {
                if (cloudPets.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            text = stringResource(id = R.string.pet).uppercase(),
                            style = MobaSubheadBold
                        )
                    }

                    items(cloudPets) { pet ->
                        ActionCardButtonWhite(
                            modifier = Modifier.padding(top = 4.dp),
                            text = pet.name.orEmpty(),
                            icon = R.drawable.ic_folder
                        ) {
                            onPetSelected(pet)
                        }
                    }
                }

                if (cloudRenters.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .padding(bottom = 16.dp),
                            text = stringResource(R.string.renters_menu).uppercase(),
                            style = MobaSubheadBold
                        )
                    }

                    items(cloudRenters) { renter ->
                        ActionCardButtonWhite(
                            modifier = Modifier.padding(top = 4.dp),
                            text = renter.name.orEmpty(),
                            icon = R.drawable.ic_folder
                        ) {
                            onRentersSelected(renter)
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun CloudItemsPreview() {
    Surface(
        modifier = Modifier
            .background(color = NeutralBackground)
    ) {
        CloudItems(
            modifier = Modifier
                .fillMaxSize()
                .background(color = NeutralBackground),
            cloudPets = listOf(
                CloudPet(
                    id = 1,
                    name = "Pet 1"
                ),
                CloudPet(
                    id = 2,
                    name = "Pet 2"
                )
            ),
            cloudRenters = listOf(
                CloudRenters(
                    id = "1",
                    name = "Renter 1"
                ),
                CloudRenters(
                    id = "2",
                    name = "Renter 2"
                )
            ),
            isRefreshing = false,
            onPetSelected = {},
            onRentersSelected = {},
            onPullToRefresh = {}
        )
    }
}

@Preview
@Composable
private fun CloudItemsLoadingPreview() {
    Surface(
        modifier = Modifier
            .background(color = NeutralBackground)
    ) {
        CloudItems(
            modifier = Modifier
                .fillMaxSize()
                .background(color = NeutralBackground),
            cloudPets = listOf(),
            cloudRenters = listOf(),
            isRefreshing = true,
            onPetSelected = {},
            onRentersSelected = {},
            onPullToRefresh = {}
        )
    }
}
