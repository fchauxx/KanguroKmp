package com.insurtech.kanguro.ui.scenes.password

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.core.utils.KeyboardUtils
import com.insurtech.kanguro.core.utils.LanguageUtils
import com.insurtech.kanguro.core.utils.onImeAction
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentPasswordBinding
import com.insurtech.kanguro.ui.MainActivity
import com.insurtech.kanguro.ui.StartActivity.Companion.IS_FILE_CLAIM
import com.insurtech.kanguro.ui.scenes.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.LoginPassword

    override val viewModel: LoginViewModel by navGraphViewModels(R.id.nav_pre_login) { defaultViewModelProviderFactory }

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreateBinding(inflater: LayoutInflater): FragmentPasswordBinding {
        return FragmentPasswordBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setObservers()
        setForgotPasswordButton()
        openBlockedUserScreen()
    }

    private fun setObservers() {
        with(viewModel) {
            loginSuccessful.observe(viewLifecycleOwner, ::onNextScreen)
            needUpdatePassword.observe(viewLifecycleOwner, ::onNeedUpdatePassword)
            updateAppLanguage.observe(viewLifecycleOwner, ::onUpdateAppLanguage)
            getShouldShowRenters().observe(viewLifecycleOwner, ::setupContinueButtonButton)
        }

        binding.passwordField.onImeAction {
            KeyboardUtils.hideKeyboard(binding.passwordField)
            viewModel.sendUserLogin()
        }
    }

    private fun onNextScreen(nextScreen: Boolean) {
        if (!nextScreen) return
        val intent = Intent(requireContext(), MainActivity::class.java)

        val isFileClaim = requireActivity().intent.getBooleanExtra(IS_FILE_CLAIM, false)
        if (isFileClaim) {
            intent.putExtra(IS_FILE_CLAIM, true)
        }

        startActivity(intent)
        requireActivity().finish()
    }

    private fun onNeedUpdatePassword(isNeeded: Boolean) {
        if (isNeeded) {
            findNavController().safeNavigate(
                PasswordFragmentDirections.toNewPasswordFragment(
                    viewModel.email.value.orEmpty(),
                    viewModel.password.value.orEmpty()
                )
            )
        }
    }

    private fun onUpdateAppLanguage(language: AppLanguage?) {
        LanguageUtils.switchLanguage(preferences, requireActivity(), language)
    }

    private fun setForgotPasswordButton() {
        binding.forgotPasswordButton.setOnClickListener {
            findNavController().safeNavigate(
                PasswordFragmentDirections.toForgotPassword(viewModel.email.value!!),
                null
            )
        }
    }

    private fun openBlockedUserScreen() {
        viewModel.isAccountBlocked.observe(viewLifecycleOwner) {
            findNavController().safeNavigate(PasswordFragmentDirections.toLoginBlocked(false))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.password.value = null
    }

    private fun setupContinueButtonButton(showRentersVersion: Boolean) {
        @StringRes
        val buttonText = if (showRentersVersion) R.string.sign_in else R.string.start_now

        @DrawableRes
        val drawable =
            if (showRentersVersion) R.drawable.ic_star_action_button else R.drawable.ic_paw_outline

        binding.continueButton.apply {
            text = getString(buttonText)
            icon =
                ContextCompat.getDrawable(requireContext(), drawable)
        }
    }
}
