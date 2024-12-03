package com.insurtech.kanguro.ui.scenes.vetAdvice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentVetAdviceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VetAdviceFragment : FullscreenFragment<FragmentVetAdviceBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.VetAdvice

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentVetAdviceBinding.inflate(inflater)

    override var lightSystemBarTint: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButton()
        setVetAdviceDescriptions()
        setInstagram()
    }

    private fun setBackButton() {
        binding.backButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun navigateTo(direction: InformationScreenType) {
        findNavController().safeNavigate(
            VetAdviceFragmentDirections.actionGlobalInformationTopicsFragment(
                direction
            )
        )
    }

    private fun setInstagram() {
        binding.doctorCard.instagramButton.setOnClickListener {
            val uri = Uri.parse("https://instagram.com/dr.b.vet?igshid=YmMyMTA2M2Y=")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun setVetAdviceDescriptions() {
        binding.dogCard.cardDog.setOnClickListener {
            navigateTo(direction = InformationScreenType.Vet_Advice_Dog)
        }
        binding.catCard.cardCat.setOnClickListener {
            navigateTo(direction = InformationScreenType.Vet_Advice_Cat)
        }
    }
}
