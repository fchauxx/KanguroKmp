package com.insurtech.kanguro.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.insurtech.kanguro.BR

abstract class BaseDialog<B : ViewBinding> : DialogFragment() {

    // Inner mutable binding
    private var _binding: B? = null

    // Binding used by subclasses
    val binding: B
        get() = _binding!!

    protected open val viewModel: ViewModel? = null

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindView(inflater, container)
        return binding.root
    }

    abstract fun onCreateBinding(inflater: LayoutInflater): B

    @CallSuper
    protected open fun bindView(inflater: LayoutInflater, container: ViewGroup?): B {
        return onCreateBinding(inflater).apply {
            if (this is ViewDataBinding) {
                lifecycleOwner = viewLifecycleOwner
                viewModel?.let { setVariable(BR.viewModel, it) }
            }
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
