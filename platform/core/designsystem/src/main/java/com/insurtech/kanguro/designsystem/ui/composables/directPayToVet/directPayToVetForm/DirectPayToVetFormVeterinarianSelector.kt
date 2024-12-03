package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroModalBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.VetInformationUi
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegularTD
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun DirectPayToVetFormVeterinarianSelector(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    allVetsInfos: List<VetInformationUi>,
    vetSearchText: String,
    onVetSearchTextChanged: (String) -> Unit,
    onVetSelected: (VetInformationUi) -> Unit,
    isVetEmailValid: Boolean,
    onContinueEmailButtonClick: (VetInformationUi) -> Unit
) {
    KanguroModalBottomSheet(
        modifier = modifier.fillMaxHeight(),
        skipPartiallyExpanded = true,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, bottom = 32.dp)
        ) {
            VetSearchTextField(
                modifier = Modifier.fillMaxWidth(),
                initialSearchText = vetSearchText,
                onTextChanged = onVetSearchTextChanged,
                isVetEmailValid = isVetEmailValid
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (allVetsInfos.isEmpty()) {
                EmptyStateComponent(
                    isEmailNotFound = vetSearchText.isNotBlank(),
                    onContinueEmailButtonClick = onContinueEmailButtonClick,
                    vetSearchText = vetSearchText
                )
            } else {
                LazyColumn {
                    items(allVetsInfos) { vetInfo ->
                        VetInformationComponent(
                            modifier = Modifier.clickable {
                                onVetSelected(vetInfo)
                                onDismiss()
                            },
                            vetInformation = vetInfo
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateComponent(
    isEmailNotFound: Boolean,
    onContinueEmailButtonClick: (VetInformationUi) -> Unit,
    vetSearchText: String
) {
    @StringRes val text = if (isEmailNotFound) {
        R.string.vet_search_not_found
    } else {
        R.string.vet_search_hint
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            painter = painterResource(id = R.drawable.img_lupe),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(16.dp))
        StyledText(text = stringResource(text))

        if (isEmailNotFound) {
            Spacer(modifier = Modifier.height(13.dp))
            UseEmailButton(
                onClick = onContinueEmailButtonClick,
                vetSearchText = vetSearchText
            )
        }
    }
}

@Composable
private fun StyledText(
    text: String
) {
    Text(
        text = text,
        style = MobaBodyRegularSDark,
        modifier = Modifier.padding(horizontal = 46.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun UseEmailButton(
    onClick: (VetInformationUi) -> Unit,
    vetSearchText: String
) {
    OutlinedButton(
        contentPadding = PaddingValues(13.dp),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
        onClick = {
            onClick(VetInformationUi(null, null, null, vetSearchText.trim()))
        }
    ) {
        Text(
            text = stringResource(id = R.string.kepp_use_email),
            textDecoration = TextDecoration.Underline,
            style = MobaBodyRegularTD,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun VetInformationComponent(
    modifier: Modifier = Modifier,
    vetInformation: VetInformationUi
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_location_small),
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = vetInformation.clinicName ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MobaBodyRegular
                )
                Text(
                    text = vetInformation.email ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MobaBodyRegular.copy(color = SecondaryMedium)
                )
            }
        }
        HorizontalDivider(thicknessDp = 1.0, color = SecondaryMedium)
    }
}

@Composable
@Preview
fun DirectPayToVetFormVeterinarianSelectorPreview() {
    Surface {
        DirectPayToVetFormVeterinarianSelector(
            onDismiss = {},
            allVetsInfos = listOf(
                VetInformationUi(
                    id = 123,
                    "Love Pet Co, 2nd St 123, Miami FL",
                    "Love Pet Co",
                    "petlove@kanguro.com"
                ),
                VetInformationUi(
                    id = 456,
                    "Nise's House, 2nd St 123, Miami FL",
                    "Nise's House",
                    "house@kanguro.com"
                ),
                VetInformationUi(
                    id = 789,
                    "Cecilia Park, 2nd St 123, Miami FL",
                    "Nise's House",
                    "ceci@kanguro.com"
                )
            ),
            vetSearchText = "",
            onVetSearchTextChanged = {},
            onVetSelected = {},
            isVetEmailValid = true,
            onContinueEmailButtonClick = {}
        )
    }
}

@Composable
@Preview
fun DirectPayToVetFormVeterinarianSelectorEmptyListPreview() {
    Surface {
        DirectPayToVetFormVeterinarianSelector(
            onDismiss = {},
            allVetsInfos = emptyList(),
            vetSearchText = "",
            onVetSearchTextChanged = {},
            onVetSelected = {},
            isVetEmailValid = false,
            onContinueEmailButtonClick = {}
        )
    }
}

@Composable
@Preview
fun VetInformationComponentPreview() {
    Surface {
        VetInformationComponent(
            modifier = Modifier.padding(16.dp),
            vetInformation = VetInformationUi(
                id = 123,
                "Love Pet Co, 2nd St 123, Miami FL",
                "Javier",
                "petlove@kanguro.com"
            )
        )
    }
}
