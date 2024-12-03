package com.insurtech.kanguro.ui.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.insurtech.kanguro.core.base.BaseDialog
import com.insurtech.kanguro.databinding.DialogReferFriendsBinding
import com.insurtech.kanguro.designsystem.px

abstract class CustomKanguroDialog : BaseDialog<DialogReferFriendsBinding>() {

    abstract fun getTitle(): String

    abstract fun getMessage(): SpannableString

    abstract fun getHeaderImage(): Int

    abstract fun onConfirmClicked(view: View)

    override fun onCreateBinding(inflater: LayoutInflater): DialogReferFriendsBinding = DialogReferFriendsBinding.inflate(inflater)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerImage.setImageResource(getHeaderImage())

        binding.referTitle.text = getTitle()

        binding.referContent.text = getMessage()

        setupListeners()
    }

    private fun setupListeners() {
        binding.referCloseButton.setOnClickListener {
            dismiss()
        }

        binding.referLaterButton.setOnClickListener {
            dismiss()
        }

        binding.referConfirmButton.setOnClickListener(::onConfirmClicked)
    }
}
