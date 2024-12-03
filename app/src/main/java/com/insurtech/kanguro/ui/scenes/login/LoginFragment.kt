package com.insurtech.kanguro.ui.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentLoginBinding
import com.insurtech.kanguro.ui.StartActivity.Companion.DEEP_EMAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.LoginEmail

    override val viewModel: LoginViewModel by navGraphViewModels(R.id.nav_pre_login) { defaultViewModelProviderFactory }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDeepLink()
        viewModel.goToNextScreen.observe(viewLifecycleOwner) {
            findNavController().safeNavigate(
                LoginFragmentDirections.actionLoginFragmentToPasswordFragment(),
                null
            )
        }
        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) {
            setupContinueButtonButton(it)
        }
    }

    private fun readDeepLink() {
        activity?.intent?.extras?.let {
            val email: String? = it.getString(DEEP_EMAIL)
            email?.let { mail ->
                viewModel.email.value = mail
            }
        }
    }

    private fun setupContinueButtonButton(showRentersVersion: Boolean) {
        @DrawableRes
        val drawable =
            if (showRentersVersion) R.drawable.ic_button_arrow_right else R.drawable.ic_paw_outline

        binding.continueButton.icon =
            ContextCompat.getDrawable(requireContext(), drawable)
    }
}
