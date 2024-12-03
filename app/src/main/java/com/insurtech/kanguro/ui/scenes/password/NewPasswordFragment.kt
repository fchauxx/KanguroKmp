package com.insurtech.kanguro.ui.scenes.password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.databinding.FragmentNewPasswordBinding
import com.insurtech.kanguro.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.NewPassword

    override val viewModel: NewPasswordViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.passwordIsValid.observe(viewLifecycleOwner) { passwordIsValid ->
            binding.saveButton.isEnabled = passwordIsValid
        }

        viewModel.newPasswordSetSuccessful.observe(viewLifecycleOwner) { navigateToHome() }

        viewModel.getShouldShowRenters().observe(viewLifecycleOwner, ::setupSaveButton)
    }

    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.sendNewPassword()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupSaveButton(showRentersVersion: Boolean) {
        binding.saveButton.icon = if (showRentersVersion) {
            null
        } else {
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_paw_outline
            )
        }
    }
}
