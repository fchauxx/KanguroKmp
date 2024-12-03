package com.insurtech.kanguro.ui.scenes.pledgeOfHonor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentPledgeOfHonorBinding
import com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase.PledgeOfHonorBaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PledgeOfHonorBottomSheet : PledgeOfHonorBaseBottomSheet() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.DirectPayToVetPledgeOfHonor

    override val viewModel: PledgeOfHonorViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentPledgeOfHonorBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setConfirmButton(REQUEST_KEY, BUNDLE_KEY)
        setupVisibility()
        onObserveViewModel()
    }

    private fun onObserveViewModel() {
        viewModel.isSigned.observe(viewLifecycleOwner) {
            binding.confirmButton.isEnabled = it
        }
    }

    private fun setupVisibility() {
        binding.headerButtons.isVisible = false
    }

    companion object {
        const val REQUEST_KEY = "request_key"
        const val BUNDLE_KEY = "bundle_key"

        fun getUserSignature(caller: Fragment, callback: (image: Bitmap) -> Unit) {
            PledgeOfHonorBottomSheet().show(caller.parentFragmentManager, null)
            caller.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                bundle.get(BUNDLE_KEY)?.let { callback(it as Bitmap) }
            }
        }

        private fun byteArrayToBitmap(data: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(data, 0, data.size)
        }
    }
}
