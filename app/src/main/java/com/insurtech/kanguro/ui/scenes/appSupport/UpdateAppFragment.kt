package com.insurtech.kanguro.ui.scenes.appSupport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentUpdateAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateAppFragment : FullscreenFragment<FragmentUpdateAppBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.UpdateApp

    override val showBottomNavigation: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentUpdateAppBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigateUp()
        updateApp()
    }

    private fun updateApp() {
        binding.updateButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=com.insurtech.kanguro"
                )
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }
    }

    private fun setNavigateUp() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }
}
