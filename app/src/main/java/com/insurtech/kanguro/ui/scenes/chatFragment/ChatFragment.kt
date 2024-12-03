package com.insurtech.kanguro.ui.scenes.chatFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentChatBinding
import com.insurtech.kanguro.designsystem.ui.composables.chat.ChatScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatEvent
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatSupportSectionModel
import com.insurtech.kanguro.domain.model.ContactInformationType
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : FullscreenFragment<FragmentChatBinding>() {

    override val screenName = AnalyticsEnums.Screen.Chat

    override val viewModel: ChatViewModel by viewModels()

    override var lightSystemBarTint: Boolean = true

    override fun onCreateBinding(inflater: LayoutInflater): FragmentChatBinding =
        FragmentChatBinding.inflate(inflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.neutral_background)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                ChatFragment(
                    viewModel = viewModel,
                    onEvent = ::handleEvent
                )
            }
        }
    }

    private fun handleEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnJavierCardPressed -> {
                val userHasBothProducts =
                    viewModel.userHasPets && viewModel.userHasRenters

                if (userHasBothProducts) {
                    viewModel.showSelectFileAClaimType(showModal = true)
                } else if (viewModel.userHasPets) {
                    navigateToJavierPetChatBot()
                } else if (viewModel.userHasRenters) {
                    viewModel.showRentersFileAClaimDialog(showDialog = true)
                }
            }

            is ChatEvent.OnCommunicationPressed -> {
                // TODO: to be implemented
            }

            is ChatEvent.OnEmailPressed -> {
                viewModel.showSelectFileAClaimType(showModal = false)
                IntentUtils.openMailToKanguro(
                    requireContext()
                )
            }

            is ChatEvent.OnPhoneCallPressed -> {
                handleContactAction(ContactInformationType.Phone)
            }

            is ChatEvent.OnSmsPressed -> handleContactAction(ContactInformationType.Sms)

            is ChatEvent.OnWhatsappPressed -> handleContactAction(ContactInformationType.Whatsapp)

            ChatEvent.OnPetFileClaimPressed -> {
                viewModel.showSelectFileAClaimType(showModal = false)
                navigateToJavierPetChatBot()
            }

            ChatEvent.OnRentersFileClaimPressed -> {
                viewModel.showSelectFileAClaimType(showModal = false)
                viewModel.showRentersFileAClaimDialog(showDialog = true)
            }

            ChatEvent.OnLiveVetPressed -> onLiveVetPressed()

            is ChatEvent.OnDismissFileAClaimTypeModal -> {
                viewModel.showSelectFileAClaimType(showModal = false)
            }

            is ChatEvent.OnDismissRentersFileAClaimDialog -> {
                viewModel.showRentersFileAClaimDialog(showDialog = false)
            }

            ChatEvent.OnTryAgainPressed -> viewModel.loadData()

            ChatEvent.PullToRefresh -> viewModel.loadData()
        }
    }

    private fun handleContactAction(type: ContactInformationType) {
        val contactInformation = viewModel.getContactInformation(type)

        if (contactInformation != null) {
            val number = contactInformation.data.number
            val text = contactInformation.data.text
            openIntent(type, number, text)
        } else {
            showContactNotAvailableAlert(type)
        }
    }

    private fun openIntent(type: ContactInformationType, number: String, text: String) {
        when (type) {
            ContactInformationType.Sms -> {
                IntentUtils.openSmsIntent(requireContext(), number, text)
            }

            ContactInformationType.Whatsapp -> {
                IntentUtils.openWhatsAppIntent(requireContext(), number, text)
            }

            ContactInformationType.Phone -> {
                IntentUtils.openDialIntent(requireContext(), number)
            }

            else -> {
            }
        }
    }

    private fun showContactNotAvailableAlert(type: ContactInformationType) {
        val message = when (type) {
            ContactInformationType.Sms -> getString(R.string.sms_contact_info_not_available)
            ContactInformationType.Whatsapp -> getString(R.string.whatsapp_contact_info_not_available)
            else -> ""
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(requireContext().getString(R.string.error_dialog_title))
            .setMessage(message)
            .setPositiveButton(requireContext().getString(R.string.back), null)
            .show()
    }

    private fun navigateToJavierPetChatBot() {
        findNavController().safeNavigate(
            ChatFragmentDirections.actionGlobalJavierChatbotFragment(
                ChatbotType.Generic
            )
        )
    }

    private fun onLiveVetPressed() {
        findNavController().safeNavigate(
            ChatFragmentDirections.actionGlobalToLiveVetBottomsheet(),
            bottomSheetLikeTransitions
        )
    }
}

@Composable
private fun ChatFragment(
    viewModel: ChatViewModel,
    onEvent: (ChatEvent) -> Unit
) {
    val chatUiState: ChatViewModel.ChatUiState by viewModel.chatUiState.collectAsState()

    ChatScreenContent(
        isLoading = chatUiState.isLoading,
        isError = chatUiState.isError,
        showSelectFileAClaimTypeDialog = chatUiState.showSelectFileAClaimTypeDialog,
        showRentersFileAClaimDialog = chatUiState.showRentersFileAClaimDialog,
        showLiveVeterinarian = chatUiState.showLiveVeterinarian,
        userHasPets = chatUiState.userHasPets,
        userHasRenters = chatUiState.userHasRenters,
        chatSupportSectionModel = chatUiState.chatSupportSectionModel,
        onEvent = onEvent
    )
}

@Composable
@Preview(device = "id:pixel_4_xl", showBackground = true)
private fun ChatFragmentPreview() {
    ChatScreenContent(
        onEvent = {},
        chatSupportSectionModel = ChatSupportSectionModel()
    )
}
