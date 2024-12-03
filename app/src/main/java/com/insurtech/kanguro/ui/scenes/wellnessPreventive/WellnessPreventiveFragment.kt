package com.insurtech.kanguro.ui.scenes.wellnessPreventive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentWhatsCoveredUsageBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WellnessPreventiveFragment :
    KanguroBottomSheetFragment<FragmentWhatsCoveredUsageBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.WellnessPreventive

    private val args: WellnessPreventiveFragmentArgs by navArgs()

    override val viewModel: WellnessPreventiveViewModel by viewModels()

    private lateinit var adapter: CoveragesItemAdapter

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentWhatsCoveredUsageBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObservers()
        setupAdapter()

        binding.whatIsCovered.visibility = View.GONE

        viewModel.getCoveragesFromSession(args.policyId, args.petId)
    }

    private fun setupListener() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.continueButton.setOnClickListener {
            viewModel.onReimbursementButtonClicked()
        }
    }

    private fun setupObservers() {
        viewModel.pet.observe(viewLifecycleOwner) {
            binding.whatIsCovered.text = requireContext().getString(R.string.pet_coverage, it.name)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.isLoading.isVisible = isLoading
            binding.whatIsCovered.isVisible = !isLoading
        }

        viewModel.reimbursementButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.continueButton.isEnabled = isEnabled
        }

        viewModel.onCoveragesSelected.observe(viewLifecycleOwner) { selectedCoverages ->
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to selectedCoverages)
            )
            dismiss()
        }
    }

    private fun setupAdapter() {
        adapter = CoveragesItemAdapter(::onItemSelected, ::onItemDeselected)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.coverages.observe(viewLifecycleOwner, adapter::submitList)
    }

    private fun onItemSelected(varName: String) {
        viewModel.onItemSelected(varName)
    }

    private fun onItemDeselected(varName: String) {
        viewModel.onItemDeselected(varName)
    }

    companion object {

        private const val REQUEST_KEY = "WellnessPreventiveFragment_request_key"
        const val BUNDLE_KEY = "WellnessPreventiveFragment_bundle_key"

        fun getSelectedCoverages(
            policyId: String,
            petId: Int,
            caller: Fragment,
            onResultReceived: (String) -> Unit
        ) {
            WellnessPreventiveFragment().apply {
                arguments =
                    WellnessPreventiveFragmentArgs.Builder(policyId, petId)
                        .build().toBundle()
            }.show(caller.parentFragmentManager, null)

            caller.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                bundle.get(BUNDLE_KEY)?.let { onResultReceived(it as String) }
            }
        }
    }
}
