package com.insurtech.kanguro.ui.scenes.coverages

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.getFormattedBreedAndAge
import com.insurtech.kanguro.databinding.LayoutCoveragesItemBinding
import com.insurtech.kanguro.domain.model.PetPolicy

@EpoxyModelClass(layout = R.layout.layout_coverages_item)
abstract class CoverageItemViewHolder : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var policy: PetPolicy? = null

    @EpoxyAttribute
    var transitionName: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ((PetPolicy, LayoutCoveragesItemBinding) -> Unit)? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? LayoutCoveragesItemBinding)?.let { binding ->
            binding.policy = this@CoverageItemViewHolder.policy
            val pet = this@CoverageItemViewHolder.policy?.pet
            binding.petBreedAndAge = pet?.getFormattedBreedAndAge(binding.root.context)
            binding.root.transitionName = transitionName
            binding.petImage.transitionName = transitionName + "image"
            binding.petNameLabel.transitionName = transitionName + "name"
            binding.root.setOnClickListener {
                clickListener?.invoke(binding.policy!!, binding)
            }
        }
    }
}
