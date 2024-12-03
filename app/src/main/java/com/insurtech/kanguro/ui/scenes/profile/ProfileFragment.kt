package com.insurtech.kanguro.ui.scenes.profile

import android.animation.LayoutTransition
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.FragmentProfileBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : KanguroBottomSheetFragment<FragmentProfileBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Profile

    override val viewModel: ProfileViewModel by viewModels()

    private val phoneClickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            val phoneNumber = getString(R.string.phone_number_support)
            IntentUtils.openDialIntent(requireContext(), phoneNumber)
        }
    }

    private val emailClickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            IntentUtils.openMailToKanguro(requireContext())
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener { dismiss() }
        setupLabels()
        setupTransitions()
        setupObservers()
        setupRequestDeletionButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().sendLocalBroadcast(KanguroConstants.BROADCAST_ACTION_PROFILE_CLOSED)
    }

    private fun setupLabels() {
        val phoneNumber = requireContext().getString(R.string.phone_number_support)
        val phoneString = requireContext().getString(
            R.string.cancel_or_change_coverage_information_0,
            phoneNumber
        )

        binding.phoneLabel.movementMethod = LinkMovementMethod.getInstance()
        binding.phoneLabel.text = SpannableString(phoneString).apply {
            setSpan(phoneClickableSpan, phoneNumber)
        }

        val emailString =
            requireContext().getString(R.string.cancel_or_change_coverage_information_1)
        val email =
            requireContext().getString(R.string.cancel_or_change_coverage_information_1_link)
        binding.emailLabel.movementMethod = LinkMovementMethod.getInstance()
        binding.emailLabel.text = SpannableString(emailString).apply {
            setSpan(emailClickableSpan, email)
        }
    }

    private fun setupTransitions() {
//        binding.listLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layoutEditProfile.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        // binding.layoutEditAddress.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layoutChangePassword.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun setupObservers() {
        viewModel.newPassword.observe(viewLifecycleOwner) { viewModel.clearPasswordErrors() }
        viewModel.repeatPassword.observe(viewLifecycleOwner) { viewModel.clearPasswordErrors() }

        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) {
            setupButtonsIcon(it)
        }
    }

    private fun setupRequestDeletionButton() {
        binding.requestDeletionButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.attention)
                .setMessage(R.string.request_deletion_alert_message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.requestDeletion()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    private fun setupButtonsIcon(isRentersEnabled: Boolean) {
        val pawIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_paw_outline)
        binding.saveProfileButton.icon = if (isRentersEnabled) null else pawIcon
        binding.changePasswordButton.icon = if (isRentersEnabled) null else pawIcon
    }
}
