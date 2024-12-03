package com.insurtech.kanguro.ui.chatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.insurtech.kanguro.databinding.FragmentFileActionsBinding

class FileSelectInputHelper(
    val context: Context,
    val action: String,
    val onSelectFilePressed: (action: String) -> Unit,
    val onSelectImagePressed: (action: String) -> Unit,
    val onOpenCameraPressed: (action: String) -> Unit
) : ChatbotInputHelper {

    private val inflater = LayoutInflater.from(context)

    override fun getLayout(): View {
        val layout = FragmentFileActionsBinding.inflate(inflater)
        layout.selectFileOption.setOnClickListener { onSelectFilePressed(action) }
        layout.selectPicOption.setOnClickListener { onSelectImagePressed(action) }
        layout.takePicOption.setOnClickListener { onOpenCameraPressed(action) }
        return layout.root
    }
}
