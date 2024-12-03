package com.insurtech.kanguro.ui.scenes.phoneValidationNew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.navGraphViewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.databinding.FragmentPhoneValidationNewBinding
import com.insurtech.kanguro.ui.SkipFocusTextWatcher
import com.insurtech.kanguro.ui.scenes.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneValidationNewFragment : BaseFragment<FragmentPhoneValidationNewBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PhoneValidation

    override val viewModel: LoginViewModel by navGraphViewModels(R.id.nav_pre_login) { defaultViewModelProviderFactory }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentPhoneValidationNewBinding {
        return FragmentPhoneValidationNewBinding.inflate(inflater).apply {
            countryCodeField.addTextChangedListener(SkipFocusTextWatcher(4, phoneField))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            // findNavController().navigate()
        }
    }
}
