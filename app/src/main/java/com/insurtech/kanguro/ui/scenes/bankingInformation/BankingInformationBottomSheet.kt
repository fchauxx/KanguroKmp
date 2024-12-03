package com.insurtech.kanguro.ui.scenes.bankingInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.core.utils.BankAccountTransformationMethod
import com.insurtech.kanguro.databinding.BottomsheetBankingInformationBinding
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankingInformationBottomSheet :
    KanguroBottomSheetFragment<BottomsheetBankingInformationBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.BankingInformation

    override val viewModel: BankingInformationViewModel by viewModels()

    private val args: BankingInformationBottomSheetArgs by navArgs()

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetBankingInformationBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBankNamePressed()
        setupButtons()
        setupBankAccountInfo()

        viewModel.userInformationUpdated.observe(viewLifecycleOwner) { userAccount ->
            setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to userAccount))
            dismiss()
        }
    }

    private fun onBankNamePressed() {
        binding.bankNameField.setOnClickListener {
            SearchBanksBottomSheet.getUserBank(this) { bank ->
                viewModel.setBankInfo(bank)
            }
        }
    }

    private fun setupBankAccountInfo() {
        binding.routingField.transformationMethod = BankAccountTransformationMethod()
        binding.accountField.transformationMethod = BankAccountTransformationMethod()
    }

    private fun setupButtons() {
        binding.continueButton.text = if (args.comingFromChatbot) {
            getString(R.string.btn_continue)
        } else {
            getString(R.string.save)
        }

        binding.continueButton.setOnClickListener {
            val selectedBank = binding.bankNameField.text.toString()

            binding.routingField.clearFocus()
            binding.accountField.clearFocus()

            viewModel.updateUserAccount(selectedBank)
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.accountSelector.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.checkingOption -> {
                    viewModel.setAccountType(AccountType.checking)
                }
                R.id.savingOption -> {
                    viewModel.setAccountType(AccountType.saving)
                }
                else -> {
                    viewModel.setAccountType(AccountType.checking)
                }
            }
        }
    }

    companion object {

        private const val REQUEST_KEY = "BankingInfoRequestCode"

        fun show(
            target: Fragment,
            comingFromChatbot: Boolean,
            onResultReceived: (UserAccount?) -> Unit
        ) {
            target.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                onResultReceived(bundle.getParcelable(REQUEST_KEY))
            }
            BankingInformationBottomSheet().apply {
                arguments = BankingInformationBottomSheetArgs.Builder().apply {
                    this.comingFromChatbot = comingFromChatbot
                }.build().toBundle()
            }.show(
                target.parentFragmentManager,
                "bankingInformationBottomSheet"
            )
        }
    }
}
