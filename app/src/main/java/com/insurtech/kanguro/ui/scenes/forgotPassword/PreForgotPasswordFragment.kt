package com.insurtech.kanguro.ui.scenes.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.BottomsheetPreForgotPasswordBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreForgotPasswordFragment :
    KanguroBottomSheetFragment<BottomsheetPreForgotPasswordBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ForgotPassword

    private val args: PreForgotPasswordFragmentArgs by navArgs()

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetPreForgotPasswordBinding {
        return BottomsheetPreForgotPasswordBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener { findNavController().navigateUp() }

        setNavigation()
    }

    override fun getOwnNavController(): NavController {
        return Navigation.findNavController(binding.forgotPasswordNavHostFragment)
    }

    private fun setNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.forgot_password_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph: NavGraph = navInflater.inflate(R.navigation.nav_forgot_password)
        navHostFragment.navController.setGraph(
            graph,
            ForgotPasswordFragmentArgs.Builder(args.email).build().toBundle()
        )
    }
}
