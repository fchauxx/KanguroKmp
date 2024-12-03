package com.insurtech.kanguro.ui.scenes.codeValidation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.KeyboardUtils
import com.insurtech.kanguro.databinding.FragmentCodeValidationBinding
import com.insurtech.kanguro.ui.SkipFocusTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeValidationFragment : FullscreenFragment<FragmentCodeValidationBinding>() {

    /*
    How this screen behaves:
    - User types the received code, 1 digit per edittext.
    - After all 4 digits are entered:
         - Disable editing on fields (enabled)
         - Close keyboard
         - Display loader and make request
    - If the result is positive, show the positive color and status for a while and go to next screen
    - If the result is negative, display the negative color and status. If the user touches any field,
    clear ALL fields and go back to the default state.
     */

    override val showBottomNavigation: Boolean = false

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CodeValidation

    override val viewModel: CodeValidationViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentCodeValidationBinding {
        return FragmentCodeValidationBinding.inflate(inflater).apply {
            code0Field.addTextChangedListener(SkipFocusTextWatcher(1, code1Field))
            code1Field.addTextChangedListener(SkipFocusTextWatcher(1, code2Field))
            code2Field.addTextChangedListener(SkipFocusTextWatcher(1, code3Field))
            code3Field.addTextChangedListener(SkipFocusTextWatcher(1, code4Field))
            code4Field.addTextChangedListener(SkipFocusTextWatcher(1, code5Field))
            code5Field.addTextChangedListener(SkipFocusTextWatcher(1, code0Field))
            codeHasntArrivedLabel.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().navigateUp() }

        setupObservers()

        viewModel.postCodeValidation()
    }

    private fun setupObservers() {
        /*We have to manually clear the focus from the fields and close keyboard, otherwise if
        the user clicks a field later in an invalid state, it will not auto-clear */
        viewModel.validationState.observe(viewLifecycleOwner) {
            if (it == CodeValidationViewModel.ValidationState.LOADING) {
                binding.codeLayout.requestFocus()
                KeyboardUtils.hideKeyboard(binding.root)
            }
        }

        viewModel.codeValidated.observe(viewLifecycleOwner) { isValid ->
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to isValid)
            )
            findNavController().navigateUp()
        }
    }

    companion object {
        const val REQUEST_KEY = "CodeValidationFragment_request_key"
        const val BUNDLE_KEY = "CodeValidationFragment_bundle_key"
    }
}
