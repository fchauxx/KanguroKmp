package com.insurtech.kanguro.ui.scenes.bankingInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.BottomsheetSearchBanksBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchBanksBottomSheet :
    KanguroBottomSheetFragment<BottomsheetSearchBanksBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.BankingInformation

    override val viewModel: SearchBanksViewModel by viewModels()

    private lateinit var adapter: BankingInformationAdapter

    private var searchedBankName = ""

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetSearchBanksBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BankingInformationAdapter(::onBankSelected)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupObservers()
        setupListeners()
        searchBank()
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.addBankName.setOnClickListener {
            onBankSelected(searchedBankName)
        }
    }

    private fun searchBank() {
        binding.bankField.doOnTextChanged { text, _, _, _ ->
            val text = text.toString().trim()
            val query = text.lowercase(Locale.getDefault())
            viewModel.updateFilter(query)

            searchedBankName = text
            binding.addBankName.text = text

            if (text.isBlank()) {
                binding.addBank.visibility = View.GONE
            } else {
                binding.addBank.visibility = View.VISIBLE
            }
        }
    }

    private fun setupObservers() {
        viewModel.banksList.observe(viewLifecycleOwner, adapter::submitList)
    }

    private fun onBankSelected(bank: String) {
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to bank))
        dismiss()
    }

    companion object {
        const val REQUEST_KEY = "request_key"
        const val BUNDLE_KEY = "bundle_key"

        fun getUserBank(caller: Fragment, callback: (bank: String) -> Unit) {
            SearchBanksBottomSheet().show(caller.parentFragmentManager, null)
            caller.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                bundle.get(BUNDLE_KEY)?.let { callback(it as String) }
            }
        }
    }
}
