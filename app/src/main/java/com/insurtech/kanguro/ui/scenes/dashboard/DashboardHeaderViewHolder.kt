package com.insurtech.kanguro.ui.scenes.dashboard

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutDashboardHeaderItemBinding
import kotlinx.coroutines.*

@EpoxyModelClass(layout = R.layout.layout_dashboard_header_item)
abstract class DashboardHeaderViewHolder(val scope: CoroutineScope, val context: Context) :
    DataBindingEpoxyModel() {

    private val CHAR_APPEAR_TIME = 50L
    private val CHAR_DISAPPEAR_TIME = 15L
    private val NAME_WAIT_TIME = 2000L

    @EpoxyAttribute
    var username: String? = null

    @EpoxyAttribute
    var petNames: List<String>? = null

    private var typewriterLoop: Job? = null

    private val color = ContextCompat.getColor(context, R.color.primary_darkest)

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        val binding = binding as? LayoutDashboardHeaderItemBinding ?: return
        binding.textHello.text = context.getString(R.string.hello)
        typewriterLoop?.cancel()
        if (petNames.isNullOrEmpty()) {
            binding.textNames.text = username
        } else if (petNames?.size == 1) {
            val name = petNames?.first() ?: return
            binding.textNames.text = getColoredTextSpan(name, name.length - 1)
        } else {
            typewriterLoop = startTypewriterAnimation(binding)
        }
    }

    private fun startTypewriterAnimation(binding: LayoutDashboardHeaderItemBinding): Job {
        return scope.launch {
            while (isActive) {
                petNames?.forEach { name ->
                    (name.indices).forEach { colorIndex ->
                        binding.textNames.text = getColoredTextSpan(name, colorIndex)
                        delay(CHAR_APPEAR_TIME)
                    }
                    delay(NAME_WAIT_TIME)
                    (name.indices).reversed().forEach { colorIndex ->
                        binding.textNames.text = getColoredTextSpan(name, colorIndex)
                        delay(CHAR_DISAPPEAR_TIME)
                    }
                }
            }
        }
    }

    private fun getColoredTextSpan(name: String, index: Int) = buildSpannedString {
        append("$username & ")
        inSpans(ForegroundColorSpan(color)) {
            append(name.substring(0, index + 1))
        }
        inSpans(ForegroundColorSpan(Color.TRANSPARENT)) {
            append(name.substring(index))
        }
    }

    override fun unbind(holder: DataBindingHolder) {
        super.unbind(holder)
        typewriterLoop?.cancel()
    }

    override fun onViewDetachedFromWindow(holder: DataBindingHolder) {
        super.onViewDetachedFromWindow(holder)
        typewriterLoop?.cancel()
    }
}
