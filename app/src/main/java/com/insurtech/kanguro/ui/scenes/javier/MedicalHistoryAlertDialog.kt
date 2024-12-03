package com.insurtech.kanguro.ui.scenes.javier

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.ui.custom.CustomKanguroDialog
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalHistoryAlertDialog : CustomKanguroDialog() {

    private val args: MedicalHistoryAlertDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.laterGroup.visibility = View.GONE
    }

    override fun getTitle(): String {
        return getString(R.string.medical_history_alert_title)
    }

    override fun getMessage(): SpannableString {
        return SpannableString(getString(R.string.medical_history_alert_message))
    }

    override fun getHeaderImage(): Int {
        return R.drawable.img_medical_history
    }

    override fun onConfirmClicked(view: View) {
        val direction = if (args.petId == 0L) {
            DashboardFragmentDirections.actionGlobalRemindersFragment()
        } else {
            DashboardFragmentDirections.actionGlobalMedicalHistoryChatbotFragment(
                ChatbotType.CompleteMedicalHistory(args.petId)
            )
        }

        findNavController().popBackStack()
        findNavController().safeNavigate(direction)
    }
}
