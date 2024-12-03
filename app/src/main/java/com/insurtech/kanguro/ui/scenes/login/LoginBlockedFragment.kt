package com.insurtech.kanguro.ui.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.databinding.BottomsheetBlockedLoginBinding
import com.insurtech.kanguro.ui.LoginActivity
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginBlockedFragment : KanguroBottomSheetFragment<BottomsheetBlockedLoginBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.LoginBlockedAccount

    private val args: LoginBlockedFragmentArgs by navArgs()

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetBlockedLoginBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSupportEmail()
        setSupportPhone()
        setCloseButton()
    }

    private fun setSupportEmail() {
        binding.contactUsEmail.setOnClickListener {
            IntentUtils.openMailToKanguro(requireContext())
        }
    }

    private fun setSupportPhone() {
        binding.contactUsPhone.setOnClickListener {
            IntentUtils.openDialIntent(requireContext(), getString(R.string.phone_number_support))
        }
    }

    private fun setCloseButton() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun startLoginActivity() {
        if (args.isUserLogged) {
            requireActivity().finish()
            startActivity(LoginActivity.newInstance(requireContext()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startLoginActivity()
    }
}
