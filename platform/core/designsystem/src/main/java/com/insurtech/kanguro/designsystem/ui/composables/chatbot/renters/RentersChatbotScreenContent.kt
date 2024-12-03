package com.insurtech.kanguro.designsystem.ui.composables.chatbot.renters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.ChatDatePickerButtonComponent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.DotsTyping
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.JavierChatbotHeader
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.JavierInitTyping
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.JavierMessageBox
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.JavierSingleTextMessage
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.SingleChoiceOptions
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.TextInputStateful
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.UserTextMessage
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ActionUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotActionTypeUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotMessageModelUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.Sender
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.getChatMessageModelListMock
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KeyboardAsState
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.Keyboard

@Composable
fun RentersChatbotScreenContent(
    modifier: Modifier = Modifier,
    messages: List<ChatbotMessageModelUi>,
    action: ActionUi,
    shouldScrollToEnd: Boolean = false,
    onKeyboardStateChange: (Boolean) -> Unit = {},
    isLoading: Boolean = false,
    navigateToScheduledItems: () -> Unit,
    navigateToOnboardingVideo: () -> Unit,
    onOnboardingFinish: () -> Unit,
    onEvent: (RentersChatbotScreenEvent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        JavierChatbotHeader(
            modifier = Modifier.fillMaxWidth()
        )
        ChatContent(
            modifier = Modifier.weight(1f),
            isLoading = isLoading,
            messages = messages,
            shouldScrollToEnd = shouldScrollToEnd,
            onKeyboardStateChange = { onKeyboardStateChange(it) }
        )
        UserInput(
            action = action,
            navigateToScheduledItems = { navigateToScheduledItems() },
            navigateToOnboardingVideo = { navigateToOnboardingVideo() },
            onOnboardingFinish = { onOnboardingFinish() }
        ) {
            onEvent(it)
        }
    }
}

@Composable
private fun ChatContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onKeyboardStateChange: (Boolean) -> Unit = {},
    messages: List<ChatbotMessageModelUi>,
    shouldScrollToEnd: Boolean = false
) {
    val listState = rememberLazyListState()

    val isKeyboardOpen by KeyboardAsState()

    onKeyboardStateChange(isKeyboardOpen == Keyboard.Opened)

    LaunchedEffect(key1 = messages.size, shouldScrollToEnd) {
        if (messages.isNotEmpty()
        ) {
            listState.animateScrollToItem(index = messages.size - 1)
        }
    }

    if (isLoading && messages.isEmpty()) {
        JavierInitTyping(modifier = Modifier.padding(24.dp))
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(24.dp)
    ) {
        itemsIndexed(messages) { index, message ->
            when (message.sender) {
                Sender.USER -> {
                    val isFirst = index == 0 || messages[index - 1].sender == Sender.CHATBOT

                    val topPadding = if (isFirst) 16.dp else 4.dp

                    UserTextMessage(
                        modifier = Modifier.padding(top = topPadding),
                        text = message.message
                    )
                }

                Sender.CHATBOT -> {
                    val isFirst = index == 0 || messages[index - 1].sender == Sender.USER

                    val topPadding = if (isFirst) 12.dp else 0.dp

                    JavierSingleTextMessage(
                        modifier = Modifier.padding(top = topPadding),
                        text = message.message,
                        isFirst = isFirst
                    )
                }
            }

            if (isLoading && index == messages.lastIndex) {
                when (messages[index].sender) {
                    Sender.USER -> JavierInitTyping()
                    else -> JavierMessageBox(
                        modifier = Modifier.padding(
                            start = 32.dp,
                            end = 32.dp,
                            bottom = 32.dp,
                            top = 4.dp
                        ),
                        isFirst = false
                    ) {
                        DotsTyping()
                    }
                }
            }
        }
    }
}

@Composable
private fun UserInput(
    action: ActionUi,
    navigateToScheduledItems: () -> Unit,
    navigateToOnboardingVideo: () -> Unit,
    onOnboardingFinish: () -> Unit,
    onEvent: (RentersChatbotScreenEvent) -> Unit
) {
    when (action) {
        is ActionUi.Text -> {
            TextInputStateful(
                onSubmit = { onEvent(RentersChatbotScreenEvent.OnTextMessageSent(it)) }
            )
        }

        is ActionUi.Date -> {
            ChatDatePickerButtonComponent {
                onEvent(RentersChatbotScreenEvent.OnDateMessageSent(it))
            }
            // TODO: add dateRange
        }

        is ActionUi.ScheduledItems -> {
            navigateToScheduledItems()
        }

        is ActionUi.SingleChoice -> {
            SingleChoiceOptions(options = action.options) {
                onEvent(RentersChatbotScreenEvent.OnOptionSelected(it))
            }
        }

        is ActionUi.CameraCaptureVideo -> {
            // TODO Handle action if user closes the upload video screen
            navigateToOnboardingVideo()
        }

        is ActionUi.Finish -> {
            onOnboardingFinish()
        }

        else -> {}
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersChatbotScreenContentPreview() {
    RentersChatbotScreenContent(
        messages = getChatMessageModelListMock(),
        action = ActionUi.Text(ChatbotActionTypeUi.Text),
        navigateToScheduledItems = {},
        onOnboardingFinish = {},
        navigateToOnboardingVideo = {}
    )
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersChatbotScreenContentLoadingPreview() {
    RentersChatbotScreenContent(
        isLoading = true,
        messages = getChatMessageModelListMock(),
        action = ActionUi.Text(ChatbotActionTypeUi.Text),
        navigateToScheduledItems = {},
        onOnboardingFinish = {},
        navigateToOnboardingVideo = {}
    )
}
