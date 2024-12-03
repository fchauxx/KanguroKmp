package com.insurtech.kanguro.ui.scenes.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentCloudFilesBinding
import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesType
import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CloudPolicyFragment : FullscreenFragment<FragmentCloudFilesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Cloud

    private val args: CloudPolicyFragmentArgs by navArgs()

    @Inject
    lateinit var cloudPolicyViewModelFactory: CloudPolicyViewModel.CloudPolicyViewModelFactory

    override val viewModel: CloudPolicyViewModel by activityViewModels {
        CloudPolicyViewModel.provideFactory(
            cloudPolicyViewModelFactory,
            args.policyId,
            args.name,
            args.cloudType
        )
    }

    private lateinit var policyFilesTypeAdapter: PolicyFilesTypeAdapter

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentCloudFilesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPolicyDocuments(args.policyId)

        setupLabels()
        setupAdapter()
        setupListeners()
        setupObservers()
    }

    private fun setupLabels() {
        val cloudName = args.name

        val folderLevel = "$cloudName / ${requireContext().getString(R.string.policy)}"
        binding.fileLevel.text = folderLevel

        binding.backText.text = when (args.cloudType) {
            CloudType.PET -> getString(R.string.pet)
            CloudType.RENTERS -> getString(R.string.renters_menu)
        }
    }

    private fun setupAdapter() {
        policyFilesTypeAdapter = PolicyFilesTypeAdapter { policyFileType ->
            viewModel.selectPolicyFileType(policyFileType)

            findNavController().navigate(
                if (policyFileType == PolicyFilesType.ClaimDocuments) {
                    CloudPolicyFragmentDirections.actionCloudPolicyFragmentToCloudClaimFragment()
                } else {
                    CloudPolicyFragmentDirections.actionCloudPolicyFragmentToCloudFileFragment()
                }
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = policyFilesTypeAdapter
    }

    private fun setupListeners() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.isVisible = it
            binding.recyclerView.isVisible = !it
        }

        viewModel.policyFilesTypes.observe(viewLifecycleOwner) { policyFilesTypes ->
            policyFilesTypeAdapter.submitList(policyFilesTypes)
        }
    }
}
