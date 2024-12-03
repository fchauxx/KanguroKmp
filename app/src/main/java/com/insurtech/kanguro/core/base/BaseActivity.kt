package com.insurtech.kanguro.core.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.insurtech.kanguro.BR
import com.insurtech.kanguro.core.base.errorHandling.handleNetworkError
import com.insurtech.kanguro.core.base.errorHandling.handleResultError

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    // Inner mutable binding
    private var _binding: B? = null

    // Binding used by subclasses
    val binding: B
        get() = _binding ?: throw IllegalStateException(
            "Trying to access the binding outside of the view lifecycle."
        )

    protected open val viewModel: ViewModel? = null

    protected open val enforceNavigationBarVisibility = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindView(layoutInflater).apply { setContentView(root) }
        updateNavigationBarColor()
    }

    abstract fun onCreateBinding(inflater: LayoutInflater): B

    @CallSuper
    protected open fun bindView(inflater: LayoutInflater): B {
        return onCreateBinding(inflater).apply {
            if (this is ViewDataBinding) {
                lifecycleOwner = this@BaseActivity
                viewModel?.let { setVariable(BR.viewModel, it) }
            }
            (viewModel as? BaseViewModel)?.networkError?.observe(
                this@BaseActivity,
                ::handleNetworkError
            )

            (viewModel as? BaseViewModel)?.resultError?.observe(
                this@BaseActivity
            ) { resultError ->
                val error = resultError.first
                val onRetry = resultError.second

                handleResultError(
                    error,
                    onRetry
                )
            }
        }
    }

    fun updateNavigationBarColor() {
        if (enforceNavigationBarVisibility && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            window.navigationBarColor = Color.BLACK
        } else {
            window.navigationBarColor = Color.TRANSPARENT
        }
    }
}
