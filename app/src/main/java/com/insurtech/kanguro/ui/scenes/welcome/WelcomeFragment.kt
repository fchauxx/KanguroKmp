package com.insurtech.kanguro.ui.scenes.welcome

import android.os.Bundle
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.NavMainDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentWelcomeBinding
import com.insurtech.kanguro.shared.Greeting
import com.insurtech.kanguro.ui.StartActivity.Companion.DEEP_EMAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : FullscreenFragment<FragmentWelcomeBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Welcome

    override var lightNavigationBar: Boolean = false

    override val viewModel: WelcomeViewModel by activityViewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentWelcomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDeepLink()
        observeShouldShowRenters()
        observeAppUpdated()
    }
    private fun readDeepLink() {
        activity?.intent?.extras?.let {
            val email: String? = it.getString(DEEP_EMAIL)
            email?.let {
                findNavController().safeNavigate(WelcomeFragmentDirections.actionWelcomeFragmentToPreLoginFragment())
            }
        }
    }

    override fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(bottom = insets.bottom)
    }

    private fun observeShouldShowRenters() {
        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) {
            binding.petVersion.root.isVisible = !it
            binding.petRentersVersion.root.isVisible = it
            if (it) setupPetRentersVersion() else setupPetVersion()
        }
    }

    private fun setupPetVersion() {
        setupPetViewPager()
        setupPetQuoteText()
        onPetSignInButtonPressed()
        onPetGetQuotePressed()
    }

    private fun setupPetRentersVersion() {
        setupPetRentersViewPager()
        setupPetRentersQuoteText()
        onPetRentersSignInButtonPressed()
        onPetRentersGetQuotePressed()
    }

    private fun setupPetViewPager() {
        binding.apply {
            petVersion.viewPager.adapter = WelcomeCarouselAdapter(this@WelcomeFragment)
            petVersion.dotsIndicator.setViewPager2(petVersion.viewPager)
            petVersion.viewPager.setPageTransformer { page, position ->
                page.findViewById<ImageView>(R.id.backgroundImage).translationX =
                    page.width * position * -1
            }
        }
    }

    private fun getQuoteText(): SpannedString {
        val quoteText = getString(R.string.get_a_quote_message)
        val toSpan = buildSpannedString {
            appendLine(quoteText)
            appendLine()

            val foregroundColorSpan = ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.secondary_darkest
                )
            )

            val relativeSizeSpan = RelativeSizeSpan(1.2f)

            bold {
                inSpans(foregroundColorSpan, relativeSizeSpan, UnderlineSpan()) {
                    append(getString(R.string.get_a_quote))
                }
            }
        }

        return toSpan
    }

    private fun setupPetQuoteText() {
        binding.petVersion.getAQuoteText.text = Greeting().greet()
    }

    private fun onPetGetQuotePressed() {
        binding.petVersion.getAQuoteText.setOnClickListener {
            findNavController().safeNavigate(
                NavMainDirections.actionGlobalGetQuoteFragment(),
                bottomSheetLikeTransitions
            )
        }
    }

    private fun onPetSignInButtonPressed() {
        binding.petVersion.signInButton.setOnClickListener {
            findNavController().safeNavigate(WelcomeFragmentDirections.actionWelcomeFragmentToPreLoginFragment())
        }
    }

    private fun observeAppUpdated() {
        viewModel.isAppUpdated.observe(viewLifecycleOwner) { isUpdated ->
            if (!isUpdated) {
                findNavController().safeNavigate(WelcomeFragmentDirections.actionGlobalUpdateAppFragment())
            }
        }
    }

    private fun setupPetRentersViewPager() {
        binding.apply {
            petRentersVersion.viewPager.adapter = WelcomeOnboardingCarouselAdapter(this@WelcomeFragment)
            petRentersVersion.dotsIndicator.setViewPager2(petRentersVersion.viewPager)
            petRentersVersion.viewPager.setPageTransformer { page, position ->
                page.findViewById<ImageView>(R.id.backgroundImage).translationX =
                    page.width * position * -1
            }
        }
    }

    private fun setupPetRentersQuoteText() {
        binding.petRentersVersion.getAQuoteText.text = Greeting().greet()
    }

    private fun onPetRentersSignInButtonPressed() {
        binding.petRentersVersion.signInButton.setOnClickListener {
            findNavController().safeNavigate(WelcomeFragmentDirections.actionWelcomeFragmentToPreLoginFragment())
        }
    }

    private fun onPetRentersGetQuotePressed() {
        binding.petRentersVersion.getAQuoteText.setOnClickListener {
            findNavController().safeNavigate(
                NavMainDirections.actionGlobalSelectProductFragment(),
                bottomSheetLikeTransitions
            )
        }
    }
}
