package com.insurtech.kanguro.ui.scenes.javier

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.Insets
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.core.utils.KanguroConstants.BROADCAST_ACTION_REFRESH_REMINDERS
import com.insurtech.kanguro.databinding.FragmentClaimsChatbotBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.chatbot.ChatbotInputHelperFactory
import com.insurtech.kanguro.ui.scenes.bankingInformation.BankingInformationBottomSheet
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.FilePickerOptionFragment
import com.insurtech.kanguro.ui.scenes.chatbot.adapter.ChatBotAdapter
import com.insurtech.kanguro.ui.scenes.codeValidation.CodeValidationFragment
import com.insurtech.kanguro.ui.scenes.fileNotSupported.FilePickerErrorHandler
import com.insurtech.kanguro.ui.scenes.javier.adapters.ChatbotHeaderAdapter
import com.insurtech.kanguro.ui.scenes.javier.adapters.ChatbotTypingAdapter
import com.insurtech.kanguro.ui.scenes.pledgeOfHonor.PledgeOfHonorBottomSheet
import com.insurtech.kanguro.ui.scenes.vetAdvice.InformationScreenType
import com.insurtech.kanguro.ui.scenes.wellnessPreventive.WellnessPreventiveFragment
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class JavierChatbotFragment :
    FullscreenFragment<FragmentClaimsChatbotBinding>(),
    ChatbotActionPressedListener {

    override var lightSystemBarTint: Boolean = true

    override val viewModel: JavierChatBotViewModel by viewModels()

    override val showBottomNavigation: Boolean = false

    @Inject
    lateinit var moshi: Moshi

    private val chatAdapter by lazy { ChatBotAdapter(moshi, ::navigateToMap) }

    private val typingAdapter = ChatbotTypingAdapter()

    private val concatAdapter by lazy {
        ConcatAdapter(ChatbotHeaderAdapter(), chatAdapter, typingAdapter)
    }

    private val filePickerHandler = FilePickerHandler(this)

    private val args: JavierChatbotFragmentArgs by navArgs()

    override val screenName: AnalyticsEnums.Screen
        get() = when (args.type) {
            is ChatbotType.NewClaim -> AnalyticsEnums.Screen.FileClaimChatbot
            is ChatbotType.Generic -> AnalyticsEnums.Screen.CentralChatbot
            is ChatbotType.CompleteMedicalHistory -> AnalyticsEnums.Screen.ReviewDocumentsChatBot
            else -> AnalyticsEnums.Screen.CentralChatbot
        }

    private val chatbotInputHelper by lazy {
        ChatbotInputHelperFactory(requireContext(), this)
    }

    private val cameraHelper = CameraHelper(this)

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentClaimsChatbotBinding.inflate(inflater)

    override fun processWindowInsets(insets: Insets) {
        super.processWindowInsets(insets)
        scrollChatToBottom()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButton()
        setupChatList()

        viewModel.chatMessages.observe(viewLifecycleOwner, ::onMessagesUpdated)
        viewModel.chatbotIsTyping.observe(viewLifecycleOwner) {
            typingAdapter.setIsTyping(it)
            scrollChatToBottom()
        }

        viewModel.interactionStep.observe(viewLifecycleOwner, ::onInteractionStepUpdated)

        viewModel.performChatbotInitialization(args.type)
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

    private fun navigateToMap() {
        findNavController().safeNavigate(JavierChatbotFragmentDirections.actionGlobalChatBotMapFragment())
    }

    private fun scrollChatToBottom() {
        binding.messageList.post {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                binding.messageList.smoothScrollToPosition(concatAdapter.itemCount)
            }
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

            ChatbotAction.LocalAction -> {
                handleLocalActionSelected(action)
            }

            ChatbotAction.FinishAndRedirect -> {
                onChatFinishedEvent(viewModel.lastChatbotAction ?: return)
            }

            ChatbotAction.Reimbursement -> {
                openReimbursementAccountScreen(ChatbotAction.Reimbursement)
            }

            ChatbotAction.StopClaim -> {
                onFinishPressed()
            }

            ChatbotAction.StopDuplicatedClaim -> {
                navigateToClaims()
            }

            ChatbotAction.FinishFilesUpload -> {
                viewModel.sendKanguroFiles()
            }

            ChatbotAction.VaccinesAndExamsSelect -> {
                val policyId = viewModel.policyId
                val petId = viewModel.petId
                if (policyId != null && petId != null) {
                    WellnessPreventiveFragment.getSelectedCoverages(
                        policyId,
                        petId,
                        this
                    ) { selectedCoverages ->
                        viewModel.getNextStep(action.action, selectedCoverages)
                        viewModel.setUserInput(selectedCoverages)
                    }
                }
            }

            ChatbotAction.OtpValidation -> {
                handleOtpValidation()
            }

            ChatbotAction.EditLastStep -> {
                viewModel.editLastStep()
            }

            ChatbotAction.SendLastStep -> {
                viewModel.sendLastStep()
            }

            else -> {
                viewModel.sendActionReply(action)
            }
        }
    }

    private fun handleLocalActionSelected(action: ChatAction) {
        when (action.value) {
            R.string.file_a_claim.toString() -> {
                viewModel.startNewFileClaimFlow(action)
            }

            R.string.vet_advice.toString() -> {
                findNavController().safeNavigate(JavierChatbotFragmentDirections.actionGlobalVetAdviceFragment())
            }

            R.string.frequent_questions.toString() -> {
                findNavController().safeNavigate(
                    JavierChatbotFragmentDirections.actionGlobalInformationTopicsFragment(
                        InformationScreenType.FAQ
                    )
                )
            }

            R.string.talk_with_support.toString() -> {
                IntentUtils.openDialIntent(
                    requireContext(),
                    getString(R.string.phone_number_support)
                )
            }

            R.string.im_a_new_pet_parent.toString() -> {
                findNavController().safeNavigate(
                    JavierChatbotFragmentDirections.actionGlobalInformationTopicsFragment(
                        InformationScreenType.NewPetParent
                    )
                )
            }
        }
    }

    private fun openReimbursementAccountScreen(action: ChatbotAction) {
        BankingInformationBottomSheet.show(this, true) {
            it?.let { viewModel.sendReimbursementReply(action, it) }
        }
    }

    private fun handleOtpValidation() {
        findNavController().safeNavigate(
            JavierChatbotFragmentDirections.actionJavierChatbotFragmentToCodeValidationFragment()
        )

        this.setFragmentResultListener(CodeValidationFragment.REQUEST_KEY) { _, bundle ->
            bundle.get(CodeValidationFragment.BUNDLE_KEY)?.let {
                it as Boolean
                viewModel.getNextStep(
                    ChatbotAction.Finish,
                    ChatbotAction.Finish.toString().lowercase()
                )
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

                is FilePickerResult.Error -> {
                    FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                }
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

    private fun onChatFinishedEvent(type: ChatbotType) {
        when (type) {
            is ChatbotType.CompleteMedicalHistory -> {
                requireContext().sendLocalBroadcast(BROADCAST_ACTION_REFRESH_REMINDERS)
            }

            is ChatbotType.NewClaim -> {
                with(findNavController()) {
                    navigateToClaims()
                    viewModel.getSubmittedClaimId()?.let { claimId ->
                        safeNavigate(
                            JavierChatbotFragmentDirections.actionGlobalExperienceFeedbackFragment(
                                claimId
                            )
                        )
                    }
                }
            }

            else -> {}
        }
    }

    private fun navigateToClaims() {
        val navOptions = navOptions {
            popUpTo(R.id.javierChatbotFragment) {
                inclusive = true
            }
        }

        with(findNavController()) {
            safeNavigate(
                JavierChatbotFragmentDirections.actionJavierChatbotFragmentToClaimsFragment(),
                navOptions
            )
        }
    }

    override fun onFinishPressed() {
        findNavController().popBackStack()
    }
}
