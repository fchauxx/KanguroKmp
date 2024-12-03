package com.insurtech.kanguro.ui.scenes.pet

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
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.core.utils.setImageSrc
import com.insurtech.kanguro.core.utils.setSpan
import com.insurtech.kanguro.databinding.FragmentUpsellingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetUpsellingFragment : FullscreenFragment<FragmentUpsellingBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PetUpselling

    override fun onCreateBinding(inflater: LayoutInflater): FragmentUpsellingBinding =
        FragmentUpsellingBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screenTitle.text = getString(R.string.pet_upselling_pet_health_plan)

        binding.image.setImageSrc(R.drawable.img_pet_upselling_for_screen)

        binding.upsellingTitle.text = getString(R.string.pet_upselling_do_you_have_a_pet)

        binding.contentText.text =
            SpannableString(getString(R.string.pet_upselling_content_text)).apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    getString(R.string.pet_upselling_content_text_plan_highlighted)
                )

                setSpan(
                    StyleSpan(Typeface.BOLD),
                    getString(R.string.pet_upselling_content_discount_highlighted)
                )
            }

        binding.continueButton.setOnClickListener {
            findNavController().safeNavigate(
                PetUpsellingFragmentDirections.actionGlobalGetQuoteFragment(),
                bottomSheetLikeTransitions
            )
        }
    }
}
