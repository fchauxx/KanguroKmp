package com.insurtech.kanguro.ui.scenes.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.databinding.BottomsheetPaymentMethodWebviewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentMethodFragment : FullscreenFragment<BottomsheetPaymentMethodWebviewBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PaymentMethod

    override val showBottomNavigation: Boolean = false
    override var lightNavigationBar: Boolean = false

    @Inject
    lateinit var sessionManager: ISessionManager

    override val viewModel: PaymentMethodViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetPaymentMethodWebviewBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.webView) {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        binding.webView.isVisible = true
                        binding.initialLoader.isVisible = false
                    }
                }
            }
        }

        binding.closeButton.setOnClickListener { findNavController().navigateUp() }

        viewModel.refreshedToken.observe(viewLifecycleOwner) { token ->
            val html = resources.openRawResource(R.raw.payment_method)
                .bufferedReader(Charsets.UTF_8)
                .use { it.readText() }
                .format(BuildConfig.CI_ENVIRONMENT, token)

            binding.webView.loadDataWithBaseURL(
                BuildConfig.WEBSITE_PET_URL,
                html,
                "text/html",
                "UTF-8",
                null
            )
        }
    }
}
