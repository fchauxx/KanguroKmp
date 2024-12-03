package com.insurtech.kanguro.designsystem.ui.composables.chat.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatEvent
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatSupportSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.OpeningHoursComponent
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs

@Composable
fun ChatSupportSection(
    modifier: Modifier = Modifier,
    chatSupportSectionModel: ChatSupportSectionModel,
    onEvent: (ChatEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.support).uppercase(),
            style = MobaCaptionBold.copy(color = SecondaryDark)
        )

        Column(
            modifier = Modifier.padding(top = spacingXxxs),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OpeningHoursComponent(
                modifier = Modifier.fillMaxWidth(),
                text = chatSupportSectionModel.openingHoursText
            )

            Spacer(modifier = Modifier.height(spacingXxxs))

            ActionCardButtonWhite(
                text = chatSupportSectionModel.actionSmsName,
                icon = R.drawable.ic_messages,
                onClick = { onEvent(ChatEvent.OnSmsPressed) },
                highlightTag = stringResource(id = R.string.action_card_highlight_tag_faster)
            )
            ActionCardButtonWhite(
                text = chatSupportSectionModel.actionWhatsappName,
                icon = R.drawable.ic_whatsapp,
                onClick = { onEvent(ChatEvent.OnWhatsappPressed) },
                highlightTag = stringResource(id = R.string.action_card_highlight_tag_faster)
            )

            Spacer(modifier = Modifier.height(spacingXxxs))

            ActionCardButtonWhite(
                text = stringResource(id = R.string.email_kanguro),
                icon = R.drawable.ic_mail,
                onClick = { onEvent(ChatEvent.OnEmailPressed) }
            )

            ActionCardButtonWhite(
                text = chatSupportSectionModel.actionPhoneName,
                icon = R.drawable.ic_phone_call_calling,
                onClick = { onEvent(ChatEvent.OnPhoneCallPressed) }
            )
        }
    }
}

@Composable
@Preview
private fun ChatSupportSectionPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ChatSupportSection(
            modifier = Modifier.padding(16.dp),
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
