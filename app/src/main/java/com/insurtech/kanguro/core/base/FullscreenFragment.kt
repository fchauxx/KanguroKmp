package com.insurtech.kanguro.core.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding

abstract class FullscreenFragment<B : ViewBinding> : BaseFragment<B>() {

    protected open var statusBarColor = Color.TRANSPARENT
    protected open var lightNavigationBar = true
    protected open var lightSystemBarTint = false
        set(value) {
            if (field != value) {
                field = value
                setupSystemBarTint()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            setupLayoutInsets()
            setupSystemBarTint()
            setupNavigationBarTint()
        }
    }

    @Suppress("DEPRECATION")
    protected fun setupSystemBarTint() {
        requireActivity().window.statusBarColor = statusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val appearance = if (lightSystemBarTint) {
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            } else {
                0
            }
            requireActivity().window.insetsController?.setSystemBarsAppearance(
                appearance,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags: Int = binding.root.systemUiVisibility
            flags = if (lightSystemBarTint) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            binding.root.systemUiVisibility = flags
        }
    }

    private fun setupNavigationBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var flags: Int = binding.root.systemUiVisibility
            flags = if (lightNavigationBar) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            binding.root.systemUiVisibility = flags
        }
    }

    private fun setupLayoutInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            processWindowInsets(insets)
            windowInsets
        }
    }

    protected open fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(top = insets.top, bottom = insets.bottom)
    }
}
