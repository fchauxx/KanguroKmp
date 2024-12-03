package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetSignatureInformation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.insurtech.kanguro.core.base.BaseDialog
import com.insurtech.kanguro.databinding.DialogDirectPayToVetSignatureInformationBinding
import com.insurtech.kanguro.designsystem.px

class DirectPayToVetSignatureInformationDialog :
    BaseDialog<DialogDirectPayToVetSignatureInformationBinding>() {

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.apply {
            val background = ColorDrawable(Color.TRANSPARENT)
            setBackgroundDrawable(InsetDrawable(background, 24.px))
            setGravity(Gravity.CENTER)
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater): DialogDirectPayToVetSignatureInformationBinding =
        DialogDirectPayToVetSignatureInformationBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.referCloseButton.setOnClickListener {
            dismiss()
        }
    }
}
