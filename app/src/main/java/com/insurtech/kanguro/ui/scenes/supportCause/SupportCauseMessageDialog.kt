package com.insurtech.kanguro.ui.scenes.supportCause

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.ui.custom.CustomKanguroDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupportCauseMessageDialog : CustomKanguroDialog() {

    override val viewModel: SupportCauseViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.donationDone.observe(viewLifecycleOwner) {
            dismiss()
        }
    }

    override fun getTitle(): String {
        return getString(R.string.support_cause_message_title)
    }

    override fun getMessage(): SpannableString {
        return SpannableString(getString(R.string.support_cause_message))
    }

    override fun getHeaderImage(): Int {
        return R.drawable.img_global
    }

    override fun onConfirmClicked(view: View) {
        findNavController().safeNavigate(
            SupportCauseMessageDialogDirections.actionGlobalSupportCause()
        )
    }
}
