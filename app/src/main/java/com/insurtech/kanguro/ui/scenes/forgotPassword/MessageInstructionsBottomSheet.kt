package com.insurtech.kanguro.ui.scenes.forgotPassword

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.BottomsheetForgotPasswordMessageBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageInstructionsBottomSheet :
    KanguroBottomSheetFragment<BottomsheetForgotPasswordMessageBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.MessageInstructions

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetForgotPasswordMessageBinding {
        return BottomsheetForgotPasswordMessageBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContactUs()
    }

    private fun setContactUs() {
        val stringSize = getString(R.string.contact_us)
        val contactUs = SpannableString(getString(R.string.please_contact_us))
        val startIndex = contactUs.indexOf(stringSize)
        val endIndex = startIndex + stringSize.length
        contactUs.apply {
            setSpan(
                CustomClickableSpan(requireContext(), requireParentFragment()),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.contactUs.text = contactUs
        binding.contactUs.movementMethod = LinkMovementMethod.getInstance()
    }
}
