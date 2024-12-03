package com.insurtech.kanguro.ui.scenes.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.setDrawableEnd
import com.insurtech.kanguro.databinding.FragmentCloudFilesBinding
import com.insurtech.kanguro.ui.scenes.cloud.adapter.CloudPolicyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloudPetFragment : FullscreenFragment<FragmentCloudFilesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Cloud

    override val viewModel: KanguroCloudViewModel by activityViewModels()

    private lateinit var cloudPolicyAdapter: CloudPolicyAdapter

    private var isDescendingOrder = true

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentCloudFilesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
        setupObservers()

        binding.fileLevel.text = viewModel.selectedCloud?.name.orEmpty()
    }

    private fun setupAdapter() {
        cloudPolicyAdapter = CloudPolicyAdapter { cloudDocumentPolicy ->
            val cloudType = viewModel.selectedCloud?.type
            val cloudDocumentPolicyId = cloudDocumentPolicy.id

            if (cloudDocumentPolicyId != null && cloudType != null) {
                findNavController().navigate(
                    CloudPetFragmentDirections.actionCloudPetFragmentToCloudPolicyFragment(
                        cloudDocumentPolicyId,
                        viewModel.selectedCloud?.name.orEmpty(),
                        cloudType
                    )
                )
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cloudPolicyAdapter
    }

    private fun setupListeners() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.reorderListButton.setOnClickListener {
            viewModel.reverseCloudPoliciesOrder()
            isDescendingOrder = !isDescendingOrder

            if (isDescendingOrder) {
                binding.reorderListButton.setDrawableEnd(R.drawable.ic_arrow_up)
            } else {
                binding.reorderListButton.setDrawableEnd(R.drawable.ic_arrow_down)
            }
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.isVisible = it
            binding.recyclerView.isVisible = !it
        }

        viewModel.selectedPolicies.observe(viewLifecycleOwner) { cloudPolicies ->
            cloudPolicyAdapter.submitList(cloudPolicies)
            binding.reorderListButton.isVisible = cloudPolicies.size > 1
        }
    }
}
