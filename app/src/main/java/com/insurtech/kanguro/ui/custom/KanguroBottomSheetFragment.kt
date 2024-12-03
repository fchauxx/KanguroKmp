package com.insurtech.kanguro.ui.custom

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseBottomSheetDialogFragment

abstract class KanguroBottomSheetFragment<T : ViewBinding> : BaseBottomSheetDialogFragment<T>() {

    protected open val isDraggable = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    private fun setupFullHeight(view: View) {
        val params = view.layoutParams
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        view.layoutParams = params
    }

    protected open fun getOwnNavController(): NavController? {
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                if (getOwnNavController()?.navigateUp() != true) {
                    dialog.dismiss()
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                it.setBackgroundResource(R.drawable.bg_bottomsheet_rounded)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
                behaviour.isDraggable = isDraggable
            }
        }
        return dialog
    }
}
