package com.insurtech.kanguro.ui.scenes.cloud

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.getFileExtension
import com.insurtech.kanguro.core.utils.openImage
import com.insurtech.kanguro.core.utils.openPdf
import com.insurtech.kanguro.databinding.FragmentCloudFilesBinding
import com.insurtech.kanguro.ui.scenes.cloud.adapter.CloudFileDocumentAdapter
import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloudFileFragment : FullscreenFragment<FragmentCloudFilesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Cloud

    override val viewModel: CloudPolicyViewModel by activityViewModels()

    private lateinit var cloudFileDocumentAdapter: CloudFileDocumentAdapter

    private val contentResolver by lazy {
        requireActivity().contentResolver
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentCloudFilesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLabels()
        setupAdapter()
        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        viewModel.clearDocuments()
        super.onDestroyView()
    }

    private fun setupLabels() {
        binding.fileLevel.text = buildFolderLevelText()
        binding.backText.text = buildBackText()
    }

    private fun buildBackText(): String {
        return when (viewModel.selectedPolicyFilesType) {
            PolicyFilesType.ClaimDocuments -> requireContext().getString(PolicyFilesType.ClaimDocuments.res)
            else -> requireContext().getString(R.string.policy)
        }
    }

    private fun buildFolderLevelText(): String {
        return when (val selectedPolicyFilesType = viewModel.selectedPolicyFilesType) {
            PolicyFilesType.ClaimDocuments -> {
                val cloudClaimDocument = viewModel.selectedCloudClaimDocument
                "${buildLevels(R.string.claim_documents)} / ${
                getString(
                    R.string.claim_number,
                    cloudClaimDocument?.claimPrefixId
                )
                }"
            }

            else -> buildLevels(selectedPolicyFilesType?.res)
        }
    }

    private fun buildLevels(@StringRes level: Int?): String {
        return if (level != null) {
            "${viewModel.name} / ${requireContext().getString(R.string.policy)} / ${
            requireContext().getString(level)
            }"
        } else {
            "${viewModel.name} / ${requireContext().getString(R.string.policy)} / ${
            requireContext().getString(R.string.policy_file)
            }"
        }
    }

    private fun setupAdapter() {
        cloudFileDocumentAdapter = CloudFileDocumentAdapter {
            viewModel.fetchCloudFile(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cloudFileDocumentAdapter
    }

    private fun setupListeners() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.cloudFileDocument.observe(viewLifecycleOwner) {
            cloudFileDocumentAdapter.submitList(it)
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

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.isVisible = it
            binding.recyclerView.isVisible = !it
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

    companion object {
        private const val PDF_EXTENSION = ".pdf"
    }
}
