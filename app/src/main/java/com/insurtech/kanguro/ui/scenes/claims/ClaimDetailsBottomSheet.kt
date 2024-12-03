package com.insurtech.kanguro.ui.scenes.claims

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.BottomsheetClaimDetailsBinding
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClaimDetailsBottomSheet : KanguroBottomSheetFragment<BottomsheetClaimDetailsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ClaimDetails

    override val viewModel: ClaimDetailsViewModel by viewModels()

    private lateinit var attachmentsAdapter: ClaimDocumentAdapter

    private val contentResolver by lazy {
        requireActivity().contentResolver
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetClaimDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closedClaim = navArgs<ClaimDetailsBottomSheetArgs>().value.claim

        setupTitle()
        setupAttachmentsList()
        setCloseButton()
        setupObservers()
        setupListeners()
    }

    private fun setupTitle() {
        val claimPrefixId = viewModel.args.claim.prefixId

        if (claimPrefixId != null) {
            binding.claimId.text = requireContext().getString(R.string.claim_number, claimPrefixId)
        }
    }

    private fun setupObservers() {
        viewModel.attachments.observe(viewLifecycleOwner) {
            attachmentsAdapter.submitList(it)
            binding.attachmentsLabel.isVisible = it.isNotEmpty()
            binding.attachmentsList.isVisible = it.isNotEmpty()
        }

        viewModel.sendingAttachments.observe(viewLifecycleOwner) { isSending ->
            binding.submitButtonLoading.isVisible = isSending
            binding.submitButton.text =
                if (!isSending) getString(R.string.submit_documents) else null
        }

        viewModel.openAttachmentEvent.observe(viewLifecycleOwner) { fileUri ->
            if (fileUri == null) {
                val alert = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(requireContext().getString(R.string.error_dialog_title))
                    .setMessage(requireContext().getString(R.string.error_dialog_generic_message))
                    .setPositiveButton(requireContext().getString(R.string.back), null)
                alert.show()
            } else {
                openDocument(fileUri)
            }
        }

        viewModel.fetchingAttachment.observe(viewLifecycleOwner) { isFetching ->
            binding.isFetchingAttachment.isVisible = isFetching
        }

        viewModel.showSubmitButton.observe(viewLifecycleOwner) { shouldShow ->
            binding.submitFrameLayout.isVisible = shouldShow
        }
    }

    private fun openDocument(fileUri: Uri) {
        val extension = fileUri.getFileExtension(contentResolver)

        if (extension == PDF_EXTENSION) {
            requireContext().openPdf(fileUri)
        } else {
            requireContext().openImage(fileUri)
        }
    }

    private fun setupAttachmentsList() {
        attachmentsAdapter = ClaimDocumentAdapter(::onDocumentPressed)
        binding.attachmentsList.layoutManager = LinearLayoutManager(requireContext())
        binding.attachmentsList.adapter = attachmentsAdapter
    }

    private fun onDocumentPressed(document: ClaimDocument) {
        viewModel.fetchAttachment(document)
    }

    private fun setCloseButton() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupListeners() {
        val claim = viewModel.args.claim

        binding.claimStatusCard.setOnClickListener {
            if (claim.hasPendingCommunications == true) {
                navigateToCommunicationChatbot(claim.id!!)
            }
        }

        binding.submitButton.setOnClickListener {
            if (claim.hasPendingCommunications == true) {
                navigateToCommunicationChatbot(claim.id!!)
            } else {
                SubmitDocumentsOptionBottomSheet.show(this, ::handleNewAttachments)
            }
        }
    }

    private fun navigateToCommunicationChatbot(claimId: String) {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalCommunicationChatbotFragment(claimId)
        )
    }

    private fun handleNewAttachments(uriList: List<Uri?>?) {
        val attachments = uriList?.mapNotNull { uri ->
            uri?.let {
                Pair(it.getBase64(contentResolver), it.getFileExtension(contentResolver))
            }
        }
        viewModel.sendAttachments(attachments)
    }

    companion object {
        private const val PDF_EXTENSION = ".pdf"
    }
}
