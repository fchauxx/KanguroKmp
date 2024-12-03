package com.insurtech.kanguro.ui.scenes.chatbot

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.FragmentAdditionalInfoChatBotBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.chatbot.ChatbotInputHelperFactory
import com.insurtech.kanguro.ui.scenes.chatbot.adapter.ChatBotAdapter
import com.insurtech.kanguro.ui.scenes.fileNotSupported.FilePickerErrorHandler
import com.insurtech.kanguro.ui.scenes.javier.ChatbotActionPressedListener
import com.insurtech.kanguro.ui.scenes.javier.adapters.ChatbotTypingAdapter
import com.insurtech.kanguro.ui.scenes.pledgeOfHonor.PledgeOfHonorBottomSheet
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdditionalInfoChatBotFragment :
    FullscreenFragment<FragmentAdditionalInfoChatBotBinding>(),
    ChatbotActionPressedListener {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.AdditionalInfoChatBot

    private val args: AdditionalInfoChatBotFragmentArgs by navArgs()

    override val viewModel: AdditionalInfoChatBotViewModel by viewModels()

    override val showBottomNavigation: Boolean = false

    @Inject
    lateinit var moshi: Moshi

    private val chatAdapter by lazy { ChatBotAdapter(moshi, ::navigateToMap) }

    private val typingAdapter = ChatbotTypingAdapter()

    private val concatAdapter by lazy {
        ConcatAdapter(chatAdapter, typingAdapter)
    }

    private val filePickerHandler = FilePickerHandler(this)

    private val cameraHelper = CameraHelper(this)

    private val chatbotInputHelper by lazy {
        ChatbotInputHelperFactory(requireContext(), this)
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentAdditionalInfoChatBotBinding.inflate(inflater)

    override fun processWindowInsets(insets: Insets) {
        binding.header.parent.updatePadding(top = insets.top)
        binding.root.updatePadding(bottom = insets.bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChatList()
        viewModel.chatMessages.observe(viewLifecycleOwner, ::onMessagesUpdated)
        viewModel.interactionStep.observe(viewLifecycleOwner, ::onInteractionStepUpdated)
        viewModel.chatbotIsTyping.observe(viewLifecycleOwner) {
            typingAdapter.setIsTyping(it)
            scrollChatToBottom()
        }
        viewModel.restoreAdditionalInfoSessions(args.pets)
        viewModel.chatFinished.observe(viewLifecycleOwner) {
            fireDashboardRefresh()
            findNavController().popBackStack()

            if (!viewModel.userHasDonated) {
                findNavController().navigate(NavDashboardDirections.actionGlobalSupportCauseMessage())
            }
        }
    }

    private fun setupChatList() {
        binding.messageList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }
    }

    private fun onMessagesUpdated(messages: List<ChatBotMessage>?) {
        chatAdapter.submitList(messages?.toList().orEmpty())
        scrollChatToBottom()
    }

    private fun onInteractionStepUpdated(step: ChatInteractionStep?) {
        binding.actionsLayout.removeAllViews()
        val newInputLayout = chatbotInputHelper.getInputForInteractionStep(step ?: return)
        binding.actionsLayout.addView(newInputLayout?.getLayout() ?: return)
    }

    override fun onButtonPressed(action: ChatAction) {
        when (action.action) {
            ChatbotAction.Signature -> {
                PledgeOfHonorBottomSheet.getUserSignature(this) { bitmap ->
                    viewModel.sendPledgeOfHonorImageReply(
                        bitmap,
                        action.userResponseMessage,
                        action.action
                    )
                }
            }

            ChatbotAction.UploadFile -> {
                FilePickerOptionFragment.show(this, action.action.toString(), this)
            }

            ChatbotAction.UploadImage -> {
                onSelectImagesPressed(action.action.toString())
            }

            ChatbotAction.EditLastStep -> {
                viewModel.editLastStep()
            }

            ChatbotAction.SendLastStep -> {
                viewModel.sendLastStep()
            }

            ChatbotAction.FinishFilesUpload -> {
                viewModel.sendKanguroFiles()
            }

            else -> {
                viewModel.sendActionReply(action)
            }
        }
    }

    override fun onUserInputSubmitted(text: String) {
        viewModel.sendUserCustomInput(text, ChatbotAction.UserCustomInput)
    }

    override fun onSelectImagesPressed(action: String) {
        val isUploadPetPicture = action == ChatbotInputType.UploadPetPicture.toString()

        filePickerHandler.openImagePicker("image/*", !isUploadPetPicture) { result ->
            when (result) {
                is FilePickerResult.Success -> {
                    handleFilePickerSuccess(isUploadPetPicture, result)
                }

                is FilePickerResult.Error -> {
                    FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                }
            }
        }
    }

    private fun handleFilePickerSuccess(
        isUploadPetPicture: Boolean,
        result: FilePickerResult.Success<List<KanguroFile>>
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (isUploadPetPicture) {
                val bitmap = BitmapFactory.decodeByteArray(
                    result.data[0].bytes,
                    0,
                    result.data[0].bytes.size
                )
                viewModel.sendPetPictureReply(
                    bitmap,
                    result.data[0].uri,
                    result.data[0].extension
                )
            } else {
                viewModel.sendImagesReply(result.data)
            }
        }
    }

    override fun onSelectFilesPressed(action: String) {
        filePickerHandler.openFilePicker("application/pdf") { result ->
            when (result) {
                is FilePickerResult.Success -> {
                    viewModel.sendFilesReply(result.data)
                }

                is FilePickerResult.Error -> {
                    FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                }
            }
        }
    }

    override fun onOpenCameraPressed(action: String) {
        val isUploadPetPicture = action == ChatbotInputType.UploadPetPicture.toString()

        cameraHelper.takePicture { image, uri ->
            if (isUploadPetPicture) {
                viewModel.sendPetPictureReply(image, uri)
            } else {
                viewModel.sendCameraImageReply(image, uri)
            }
        }
    }

    override fun onFinishPressed() {
        fireDashboardRefresh()
        findNavController().popBackStack()
    }

    private fun navigateToMap() {
        findNavController().safeNavigate(
            AdditionalInfoChatBotFragmentDirections.actionChatBotFragmentToChatBotMapFragment()
        )
    }

    private fun fireDashboardRefresh() {
        requireContext().sendLocalBroadcast(KanguroConstants.BROADCAST_ACTION_REFRESH_REMINDERS)
        requireContext().sendLocalBroadcast(KanguroConstants.BROADCAST_ACTION_REFRESH_POLICIES)
    }

    private fun scrollChatToBottom() {
        binding.messageList.post {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                binding.messageList.smoothScrollToPosition(concatAdapter.itemCount)
            }
        }
    }
}
