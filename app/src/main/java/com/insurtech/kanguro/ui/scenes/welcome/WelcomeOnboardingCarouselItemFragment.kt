package com.insurtech.kanguro.ui.scenes.welcome

import android.content.SharedPreferences
import android.os.Bundle
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.core.view.isVisible
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseFragment
import com.insurtech.kanguro.core.utils.LanguageUtils
import com.insurtech.kanguro.databinding.FragmentOnboardingCarouselItemBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val POSITION = "position"
private const val FIRST_INDEX = 0

@AndroidEntryPoint
class WelcomeOnboardingCarouselItemFragment :
    BaseFragment<FragmentOnboardingCarouselItemBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CarouselItem

    private var position: Int? = null

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentOnboardingCarouselItemBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPageInfo()
    }

    private fun setPageInfo() {
        when (position) {
            FIRST_INDEX -> pageOne()
            else -> pageTwo()
        }
    }

    private fun pageOne() {
        binding.apply {
            backgroundImage.setImageResource(R.drawable.image_onboarding_1)

            contentText.text = buildSpannedString {
                appendLine(getString(R.string.first_content_0))
                inSpans(RelativeSizeSpan(0.87f)) {
                    color(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.secondary_dark
                        )
                    ) {
                        append(getString(R.string.onboarding_1))
                    }
                }
            }

            switchLanguage.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    LanguageUtils.switchLanguage(preferences, requireActivity())
                }
            }
        }
    }

    private fun pageTwo() {
        binding.apply {
            backgroundImage.setImageResource(R.drawable.image_onboarding_2)
            contentText.text = getString(R.string.second_content)
            switchLanguage.isVisible = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            WelcomeOnboardingCarouselItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
    }
}
