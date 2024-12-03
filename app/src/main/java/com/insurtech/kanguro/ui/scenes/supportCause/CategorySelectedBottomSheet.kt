package com.insurtech.kanguro.ui.scenes.supportCause

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.BottomsheetCategorySelectedBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySelectedBottomSheet :
    KanguroBottomSheetFragment<BottomsheetCategorySelectedBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.SelectYourCause

    override val viewModel: SupportCauseViewModel by activityViewModels()

    lateinit var adapter: CauseItemAdapter

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetCategorySelectedBinding =
        BottomsheetCategorySelectedBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservers()
        setupListeners()

        binding.logoCause.setImageResource(viewModel.selectedCause!!.icon)
        binding.titleCause.text = getString(viewModel.selectedCause!!.title)

        viewModel.getFilteredCharityList()
    }

    private fun setupAdapter() {
        adapter = CauseItemAdapter(::onCharitySelected)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.filteredCharityList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onCharitySelected(item: CharityListItem) {
        viewModel.onCharitySelected(item.charityResponse)
    }
}
