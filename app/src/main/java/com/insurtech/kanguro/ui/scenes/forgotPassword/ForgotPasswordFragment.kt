package com.insurtech.kanguro.ui.scenes.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ForgotPassword

    override val viewModel: ForgotPasswordViewModel by navGraphViewModels(R.id.nav_forgot_password) { defaultViewModelProviderFactory }

    private val args: ForgotPasswordFragmentArgs by navArgs()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentForgotPasswordBinding {
        return FragmentForgotPasswordBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goToNextScreen.observe(viewLifecycleOwner) {
            findNavController().safeNavigate(ForgotPasswordFragmentDirections.toDialogInstructions(), null)
        }
        setEmailField()
    }

    private fun setEmailField() {
        viewModel.setInitialEmail(args.email)
    }
}
