package com.insurtech.kanguro.ui.scenes.coverages

import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutCoverageFilterItemBinding
import com.insurtech.kanguro.domain.dashboard.CoveragesFilter

@EpoxyModelClass(layout = R.layout.layout_coverage_filter_item)
abstract class CoverageFilterItemViewHolder : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var filterVisible: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ((CoveragesFilter) -> Unit)? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? LayoutCoverageFilterItemBinding)?.let { binding ->
            binding.buttonAll.isChecked = true
            binding.radioGroup.isVisible = filterVisible

            binding.radioGroup.setOnCheckedChangeListener { it, _ ->
                val selectedButton = it.checkedRadioButtonId
                clickListener?.invoke(setCoveragesFilter(binding, selectedButton))
            }
        }
    }

    private fun setCoveragesFilter(
        binding: LayoutCoverageFilterItemBinding,
        filterButton: Int
    ): CoveragesFilter {
        var filter = CoveragesFilter.All

        when (filterButton) {
            binding.buttonAll.id -> {
                filter = CoveragesFilter.All
            }
            binding.buttonActive.id -> {
                filter = CoveragesFilter.Active
            }
            binding.buttonInactive.id -> {
                filter = CoveragesFilter.Inactive
            }
        }
        return filter
    }
}
