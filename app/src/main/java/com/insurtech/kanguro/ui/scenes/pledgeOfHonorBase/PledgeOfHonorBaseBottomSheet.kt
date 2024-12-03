package com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase

import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.gcacace.signaturepad.views.SignaturePad
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.core.utils.setSpan
import com.insurtech.kanguro.databinding.FragmentPledgeOfHonorBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment

abstract class PledgeOfHonorBaseBottomSheet :
    KanguroBottomSheetFragment<FragmentPledgeOfHonorBinding>() {

    private lateinit var signatureReceived: Bitmap

    override val viewModel: PledgeOfHonorBaseViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentPledgeOfHonorBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCloseButton()
        setCleanButton()
        setBottomText()
        setupAuthorizationAgree()

        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }

            override fun onSigned() {
                viewModel.toggleButton(true)
            }

            override fun onClear() {
                viewModel.toggleButton(false)
            }
        })
    }

    private fun setCloseButton() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    protected open fun setConfirmButton(requestKey: String, bundleKey: String) {
        binding.confirmButton.setOnClickListener {
            signatureReceived = binding.signaturePad.transparentSignatureBitmap
            setFragmentResult(requestKey, bundleOf(bundleKey to signatureReceived))
            dismiss()
        }
    }

    private fun setCleanButton() {
        binding.cleanSignaturePad.setOnClickListener {
            binding.signaturePad.clearView()
        }
    }

    private fun setBottomText() {
        binding.textView.text = if (viewModel.supportedCauseTitle != null) {
            SpannableString(getString(R.string.donated_to, viewModel.supportedCauseTitle)).apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    viewModel.supportedCauseTitle!!
                )
            }
        } else {
            getString(R.string.donated_to, getString(R.string.a_cause))
        }
    }

    private fun setupAuthorizationAgree() {
        val agreeText =
            SpannableString(getString(R.string.direct_pay_to_vet_medical_authorization_agree))

        val readMoreText = SpannableString(getString(R.string.read_more))

        readMoreText.apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tertiary_extra_dark
                    )
                ),
                0,
                readMoreText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                readMoreText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                UnderlineSpan(),
                0,
                readMoreText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val combinedString =
            SpannableStringBuilder().append(agreeText).append(" ").append(readMoreText)

        binding.agreeText.apply {
            setText(combinedString, TextView.BufferType.SPANNABLE)
            setOnClickListener {
                findNavController()
                    .safeNavigate(
                        NavDashboardDirections.actionGlobalDirectPayToVetSignatureInformationDialog2()
                    )
            }
        }
    }
}
