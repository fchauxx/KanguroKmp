package com.insurtech.kanguro.designsystem.ui.composables.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatEvent
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatSupportSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.chat.sections.ChatEmergencySection
import com.insurtech.kanguro.designsystem.ui.composables.chat.sections.ChatSupportSection
import com.insurtech.kanguro.designsystem.ui.composables.chat.sections.JavierCardSection
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.LoadingErrorStateOverlay
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.FileClaimDialog
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.FileAClaimTypeModalBottomSheet
import com.insurtech.kanguro.designsystem.ui.theme.BKLHeading3
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground20
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground40
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground5
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground80
import com.insurtech.kanguro.designsystem.ui.theme.spacingXs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    userHasPets: Boolean = true,
    userHasRenters: Boolean = true,
    showSelectFileAClaimTypeDialog: Boolean = false,
    showRentersFileAClaimDialog: Boolean = false,
    showLiveVeterinarian: Boolean = false,
    chatSupportSectionModel: ChatSupportSectionModel = ChatSupportSectionModel(),
    onEvent: (ChatEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(
                ChatEvent.PullToRefresh
            )
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        FileAClaimTypeModalBottomSheet(
            modalBottomSheet = showSelectFileAClaimTypeDialog,
            showRentersOption = userHasRenters,
            showPetOption = userHasPets,
            onDismiss = {
                onEvent(ChatEvent.OnDismissFileAClaimTypeModal)
            },
            onPetFileAClaim = {
                onEvent(ChatEvent.OnPetFileClaimPressed)
            },
            onRentersFileAClaim = {
                onEvent(ChatEvent.OnRentersFileClaimPressed)
            }
        )

        FileClaimDialog(
            showFileClaimDialog = showRentersFileAClaimDialog,
            onClick = {
                onEvent(ChatEvent.OnEmailPressed)
            },
            onDismiss = {
                onEvent(ChatEvent.OnDismissRentersFileAClaimDialog)
            }
        )

        ParentContent(
            modifier = Modifier
                .background(color = NeutralBackground)
                .padding(top = spacingXxxs)
                .fillMaxSize(),
            isLoading = isLoading,
            isError = isError,
            userHasPets = userHasPets,
            chatSupportSectionModel = chatSupportSectionModel,
            showLiveVeterinarian = showLiveVeterinarian,
            onEvent = onEvent
        )

        LoadingErrorStateOverlay(
            isLoading = isLoading,
            isError = isError,
            onTryAgainPressed = { onEvent(ChatEvent.OnTryAgainPressed) }
        )

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ParentContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    userHasPets: Boolean,
    chatSupportSectionModel: ChatSupportSectionModel,
    showLiveVeterinarian: Boolean,
    onEvent: (ChatEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Title(
            modifier = Modifier
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!isLoading && !isError) {
                Content(
                    userHasPets = userHasPets,
                    modifier = Modifier
                        .fillMaxSize(),
                    chatSupportSectionModel = chatSupportSectionModel,
                    showLiveVeterinarian = showLiveVeterinarian,
                    onEvent = onEvent
                )
            }

            TopGradientLine(
                colors = listOf(
                    NeutralBackground,
                    NeutralBackground80,
                    NeutralBackground40,
                    NeutralBackground20,
                    NeutralBackground5
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )

            BottomGradientAlpha5(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(horizontal = spacingXxs)
            .fillMaxWidth(),
        text = stringResource(id = R.string.chat_title),
        style = BKLHeading3
    )
}

@Composable
private fun Content(
    userHasPets: Boolean,
    chatSupportSectionModel: ChatSupportSectionModel,
    showLiveVeterinarian: Boolean,
    modifier: Modifier,
    onEvent: (ChatEvent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacingXxs)
    ) {
        JavierCardSection(
            modifier = Modifier.padding(top = spacingXs),
            onClick = { onEvent(ChatEvent.OnJavierCardPressed) }
        )

        if (userHasPets && showLiveVeterinarian) {
            ChatEmergencySection(
                modifier = Modifier.padding(top = spacingXs),
                onEvent = onEvent
            )
        }

        ChatSupportSection(
            modifier = Modifier.padding(top = spacingXs),
            chatSupportSectionModel = chatSupportSectionModel,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(spacingXxxs))
    }
}

@Preview
@Composable
private fun ChatScreenContentPreview() {
    Surface {
        ChatScreenContent(
            chatSupportSectionModel = ChatSupportSectionModel(
                openingHoursText = stringResource(id = R.string.working_hours),
                actionSmsName = stringResource(id = R.string.chat_sms),
                actionWhatsappName = stringResource(id = R.string.chat_whatsapp),
                actionPhoneName = stringResource(id = R.string.call_us)
            ),
            showLiveVeterinarian = true,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ChatScreenContentLoadingPreview() {
    Surface {
        ChatScreenContent(
            isLoading = true,
            isError = false,
            chatSupportSectionModel = ChatSupportSectionModel(
                openingHoursText = stringResource(id = R.string.working_hours),
                actionSmsName = stringResource(id = R.string.chat_sms),
                actionWhatsappName = stringResource(id = R.string.chat_whatsapp),
                actionPhoneName = stringResource(id = R.string.call_us)
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ChatScreenContentErrorPreview() {
    Surface {
        ChatScreenContent(
            isLoading = false,
            isError = true,
            chatSupportSectionModel = ChatSupportSectionModel(
                openingHoursText = stringResource(id = R.string.working_hours),
                actionSmsName = stringResource(id = R.string.chat_sms),
                actionWhatsappName = stringResource(id = R.string.chat_whatsapp),
                actionPhoneName = stringResource(id = R.string.call_us)
            ),
            onEvent = {}
        )
    }
}
