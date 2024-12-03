package com.insurtech.kanguro.ui.scenes.paymentSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentPaymentSettingsBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSettingsFragment : FullscreenFragment<FragmentPaymentSettingsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PaymentSettings

    override var lightSystemBarTint: Boolean = true

    override val viewModel: DashboardViewModel by activityViewModels()

    private val controller: PaymentSettingsEpoxyController by lazy {
        PaymentSettingsEpoxyController(
            requireContext(),
            ::onPaymentMethodPressed,
            ::onReimbursementAccountPressed
        )
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentPaymentSettingsBinding.inflate(inflater).apply {
            recyclerView.setControllerAndBuildModels(controller)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButton()
        viewModel.coveragesList.observe(viewLifecycleOwner) {
            controller.policyList = it
        }

        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            binding.recyclerView.isVisible = state is DashboardViewModel.State.Data
        }

        composeLoading()

        initIfRentersOn()
    }

    private fun initIfRentersOn() {
        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) {
            if (it) {
                viewModel.paymentSettingsRefresh()
            }
        }
    }

    private fun composeLoading() {
        binding.composeLoader.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val isLoading = viewModel.isPaymentSettingsLoading.collectAsState()

                if (isLoading.value) {
                    ScreenLoader(
                        color = White
                    )
                }
            }
        }
    }

    private fun setBackButton() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onPaymentMethodPressed() {
        findNavController().safeNavigate(
            PaymentSettingsFragmentDirections.actionPaymentSettingsFragmentToPaymentMethodFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun onReimbursementAccountPressed() {
        findNavController().safeNavigate(
            PaymentSettingsFragmentDirections
                .actionPaymentSettingsFragmentToBankingInformationBottomSheet()
        )
    }
}
