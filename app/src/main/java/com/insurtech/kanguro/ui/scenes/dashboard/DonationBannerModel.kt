package com.insurtech.kanguro.ui.scenes.dashboard

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.DonationBanner
import com.insurtech.kanguro.ui.epoxy.KotlinEpoxyHolder

@EpoxyModelClass
abstract class DonationBannerModel : EpoxyModelWithHolder<DonationBannerModel.Holder>() {

    @EpoxyAttribute
    lateinit var modifier: Modifier

    @EpoxyAttribute
    lateinit var onClick: () -> Unit

    override fun getDefaultLayout() = R.layout.layout_donation_banner

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.composeView.setContent {
            DonationBanner(modifier = modifier, onClick = onClick)
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.composeView.disposeComposition()
    }

    class Holder : KotlinEpoxyHolder() {
        val composeView by bind<ComposeView>(R.id.donation_banner_compose_view)
    }
}
