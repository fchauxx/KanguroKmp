package com.insurtech.kanguro.ui.scenes.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.BottomsheetWebviewBinding

abstract class WebViewFragment : FullscreenFragment<BottomsheetWebviewBinding>() {

    override val showBottomNavigation: Boolean = false
    override var lightNavigationBar: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetWebviewBinding.inflate(inflater)

    abstract fun getWebViewUrl(): String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.webView) {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
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
            loadUrl(getWebViewUrl())
        }

        binding.closeButton.setOnClickListener { findNavController().navigateUp() }
    }
}
