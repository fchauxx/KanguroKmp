package com.insurtech.kanguro.ui.scenes.whatsCoveredUsage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentWhatsCoveredUsageBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import com.insurtech.kanguro.ui.scenes.whatsCovered.WhatsCoveredFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatsCoveredUsageFragment : KanguroBottomSheetFragment<FragmentWhatsCoveredUsageBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PreventiveWhatsCovered

    override val viewModel: WhatsCoveredViewModel by viewModels()

    private val args: WhatsCoveredUsageFragmentArgs by navArgs()

    private lateinit var adapter: WhatsCoveredItemAdapter

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentWhatsCoveredUsageBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()

        setupAdapter()

        viewModel.fetchCoverageItems(args.policy)
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener { dismiss() }

        binding.continueButton.setOnClickListener {
            findNavController().safeNavigate(
                WhatsCoveredFragmentDirections.actionGlobalJavierChatbotFragment(ChatbotType.NewClaim(null))
            )
        }
    }
    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.isLoading.isVisible = isLoading
        }
    }

    private fun setupAdapter() {
        adapter = WhatsCoveredItemAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.coverageItems.observe(viewLifecycleOwner, adapter::submitList)
    }
}
