package com.insurtech.kanguro.ui.scenes.javier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.CameraHelper
import com.insurtech.kanguro.core.utils.FilePickerHandler
import com.insurtech.kanguro.databinding.FragmentClaimsChatbotBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.chatbot.ChatbotInputHelperFactory
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.FilePickerOptionFragment
import com.insurtech.kanguro.ui.scenes.chatbot.adapter.ChatBotAdapter
import com.insurtech.kanguro.ui.scenes.fileNotSupported.FilePickerErrorHandler
import com.insurtech.kanguro.ui.scenes.javier.adapters.ChatbotHeaderAdapter
import com.insurtech.kanguro.ui.scenes.javier.adapters.ChatbotTypingAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CommunicationChatbotFragment :
    FullscreenFragment<FragmentClaimsChatbotBinding>(),
    ChatbotActionPressedListener {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CommunicationChatbot

    override val showBottomNavigation: Boolean = false

    override val viewModel: CommunicationChatbotViewModel by viewModels()

    @Inject
    lateinit var moshi: Moshi

    private val chatAdapter by lazy { ChatBotAdapter(moshi) { } }

    private val typingAdapter = ChatbotTypingAdapter()

    private val concatAdapter by lazy {
        ConcatAdapter(ChatbotHeaderAdapter(), chatAdapter, typingAdapter)
    }

    private val chatbotInputHelper by lazy {
        ChatbotInputHelperFactory(requireContext(), this)
    }

    private val filePickerHandler = FilePickerHandler(this)

    private val cameraHelper = CameraHelper(this)

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentClaimsChatbotBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButton()
        setupChatList()

        viewModel.startChat()

        viewModel.chatMessages.observe(viewLifecycleOwner, ::onMessagesUpdated)

        viewModel.chatbotIsTyping.observe(viewLifecycleOwner) {
            typingAdapter.setIsTyping(it)
            scrollChatToBottom()
        }

        viewModel.interactionStep.observe(viewLifecycleOwner, ::onInteractionStepUpdated)

        viewModel.chatFinished.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun setBackButton() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
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

    private fun scrollChatToBottom() {
        binding.messageList.post {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                binding.messageList.smoothScrollToPosition(concatAdapter.itemCount)
            }
        }
    }

    private fun onInteractionStepUpdated(step: ChatInteractionStep?) {
        binding.actionsLayout.removeAllViews()
        val newInputLayout = chatbotInputHelper.getInputForInteractionStep(step ?: return)
        binding.actionsLayout.addView(newInputLayout?.getLayout() ?: return)
    }

    override fun onButtonPressed(action: ChatAction) {
        when (action.action) {
            ChatbotAction.UploadFile -> {
                FilePickerOptionFragment.show(this, action.action.toString(), this)
            }
            ChatbotAction.Finish -> {
                onFinishPressed()
            }
            ChatbotAction.StopClaim -> {
                findNavController().popBackStack()
            }
            else -> {
                // Left empty on purpose
            }
        }
    }

    override fun onUserInputSubmitted(text: String) {
        // Left empty on purpose
    }

    override fun onSelectImagesPressed(action: String) {
        filePickerHandler.openImagePicker("image/*") { result ->
            when (result) {
                is FilePickerResult.Success -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.sendImagesReply(result.data)
                    }
                }
                is FilePickerResult.Error -> {
                    FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                }
            }
        }
    }

    override fun onSelectFilesPressed(action: String) {
        filePickerHandler.openImagePicker("image/*") { result ->
            when (result) {
                is FilePickerResult.Success -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.sendImagesReply(result.data)
                    }
                }
                is FilePickerResult.Error -> {
                    FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                }
            }
        }
    }

    override fun onOpenCameraPressed(action: String) {
        cameraHelper.takePicture { image, uri ->
            viewModel.sendImageReply(image, uri)
        }
    }

    override fun onFinishPressed() {
        viewModel.saveUserFiles()
    }
}
