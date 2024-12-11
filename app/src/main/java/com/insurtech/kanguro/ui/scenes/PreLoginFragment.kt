package com.insurtech.kanguro.ui.scenes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentPreLoginBinding
import com.insurtech.kanguro.shared.App
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginFragment : KanguroBottomSheetFragment<FragmentPreLoginBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.LoginEmail

    override fun onCreateBinding(inflater: LayoutInflater): FragmentPreLoginBinding {
        return FragmentPreLoginBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener { findNavController().navigateUp() }
        binding.composeView.setContent {
            App()
        }
    }

    override fun getOwnNavController(): NavController {
        return Navigation.findNavController(binding.loginNavHostFragment)
    }
}
