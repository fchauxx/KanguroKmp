package com.insurtech.kanguro.ui.scenes.renters

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.setImageSrc
import com.insurtech.kanguro.core.utils.setSpan
import com.insurtech.kanguro.databinding.FragmentUpsellingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersUpsellingFragment : FullscreenFragment<FragmentUpsellingBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.RentersUpselling

    override fun onCreateBinding(inflater: LayoutInflater): FragmentUpsellingBinding =
        FragmentUpsellingBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screenTitle.text = getString(R.string.renters_upselling_renters_insurance)

        binding.image.setImageSrc(R.drawable.img_renters_upselling_for_screen)

        binding.upsellingTitle.text = getString(R.string.renters_upselling_do_you_rent_a_place)

        binding.contentText.text =
            SpannableString(getString(R.string.renters_upselling_content_text)).apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    getString(R.string.renters_upselling_content_text_plan_highlighted)
                )

                setSpan(
                    StyleSpan(Typeface.BOLD),
                    getString(R.string.renters_upselling_content_discount_highlighted)
                )
            }

        binding.continueButton.setOnClickListener {
            findNavController().navigate(
                RentersUpsellingFragmentDirections.actionGlobalGetQuoteRentersFragment(),
                bottomSheetLikeTransitions
            )
        }
    }
}
