package com.insurtech.kanguro.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.insurtech.kanguro.BR
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.analytics.IAnalyticsService
import com.insurtech.kanguro.core.base.errorHandling.handleNetworkError
import com.insurtech.kanguro.core.base.errorHandling.handleResultError
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<B : ViewBinding> : BottomSheetDialogFragment() {

    protected abstract val screenName: AnalyticsEnums.Screen

    @Inject
    lateinit var analyticsService: IAnalyticsService

    // Inner mutable binding
    private var _binding: B? = null

    // Binding used by subclasses
    val binding: B
        get() = _binding!!

    protected open val viewModel: ViewModel? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container)
        return binding.root
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        analyticsService.analyticsLogScreen(screenName)
    }

    abstract fun onCreateBinding(inflater: LayoutInflater): B

    @CallSuper
    protected open fun bindView(inflater: LayoutInflater, container: ViewGroup?): B {
        return onCreateBinding(inflater).apply {
            if (this is ViewDataBinding) {
                lifecycleOwner = viewLifecycleOwner
                viewModel?.let { setVariable(BR.viewModel, it) }
            }

            (viewModel as? BaseViewModel)?.networkError?.observe(
                viewLifecycleOwner,
                ::handleNetworkError
            )

            (viewModel as? BaseViewModel)?.resultError?.observe(
                viewLifecycleOwner
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

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
