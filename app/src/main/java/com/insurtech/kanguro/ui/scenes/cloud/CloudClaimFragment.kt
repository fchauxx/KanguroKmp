package com.insurtech.kanguro.ui.scenes.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentCloudFilesBinding
import com.insurtech.kanguro.ui.scenes.cloud.adapter.CloudClaimAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloudClaimFragment : FullscreenFragment<FragmentCloudFilesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Cloud

    override val viewModel: CloudPolicyViewModel by activityViewModels()

    lateinit var cloudClaimAdapter: CloudClaimAdapter

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
        val folderLevel = "${viewModel.name} / ${requireContext().getString(R.string.policy)} / ${
        requireContext().getString(R.string.claim_documents)
        }"

        binding.fileLevel.text = folderLevel

        binding.backText.text = requireContext().getString(R.string.policy)
    }

    private fun setupAdapter() {
        cloudClaimAdapter = CloudClaimAdapter {
            viewModel.selectCloudClaim(it)

            findNavController().navigate(
                CloudClaimFragmentDirections.actionCloudClaimFragmentToCloudFileFragment()
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cloudClaimAdapter
    }

    private fun setupListeners() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.cloudClaimDocuments.observe(viewLifecycleOwner) { cloudClaimDocuments ->
            cloudClaimAdapter.submitList(cloudClaimDocuments)
        }
    }
}
