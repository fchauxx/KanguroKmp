package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetPledgeOfHonor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentPledgeOfHonorBinding
import com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase.PledgeOfHonorBaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetPledgeOfHonorBottomSheet : PledgeOfHonorBaseBottomSheet() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PledgeOfHonor

    override val viewModel: DirectPayToVetPledgeOfHonorViewModel by viewModels()

    override val isDraggable: Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentPledgeOfHonorBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setConfirmButton("", "")
        setButtonText()
        setupVisibility()
        onBackPressed()
        onCloseButtonPressed()
        onObserveViewModel()
    }

    override fun setConfirmButton(requestKey: String, bundleKey: String) {
        binding.confirmButton.setOnClickListener {
            val signatureReceived = binding.signaturePad.transparentSignatureBitmap
            viewModel.handleSignature(signatureReceived)
        }
    }

    private fun onObserveViewModel() {
        viewModel.isButtonEnable.observe(viewLifecycleOwner) {
            binding.confirmButton.isEnabled = it
        }

        viewModel.claimInformationResponse.observe(viewLifecycleOwner) { claimShareInformation ->

            claimShareInformation?.let { claimId ->

                val navOptions = navOptions {
                    popUpTo(R.id.directPayToVetInitFlowFragment) {
                        inclusive = true
                    }
                }

                findNavController().safeNavigate(
                    DirectPayToVetPledgeOfHonorBottomSheetDirections.actionDirectPayToVetPledgeOfHonorBottomSheetToDirectPayToVetAlmostDone(
                        claimId
                    ),
                    navOptions
                )
            } ?: run {
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        val context = requireContext()
        val alert = MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.error_dialog_title))
            .setMessage(context.getString(R.string.error_dialog_generic_message))
            .setPositiveButton(context.getString(R.string.back), null)

        alert.show()
    }

    private fun onBackPressed() {
        binding.headerBackButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onCloseButtonPressed() {
        binding.headerCloseButton.setOnClickListener {
            findNavController().popBackStack(R.id.directPayToVetInitFlowFragment, true)
        }
    }

    private fun setupVisibility() {
        binding.closeButton.isVisible = false
    }

    private fun setButtonText() {
        binding.confirmButton.setText(R.string.send_form)
    }
}
