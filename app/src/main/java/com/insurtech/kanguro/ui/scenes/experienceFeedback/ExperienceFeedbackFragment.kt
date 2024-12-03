package com.insurtech.kanguro.ui.scenes.experienceFeedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentExperienceFeedbackBinding
import com.insurtech.kanguro.designsystem.ui.composables.experienceFeedback.RadioGroupRating
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryMedium
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperienceFeedbackFragment : FullscreenFragment<FragmentExperienceFeedbackBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ExperienceFeedback

    override val viewModel: ExperienceFeedbackViewModel by viewModels()

    private val args: ExperienceFeedbackFragmentArgs by navArgs()

    override val showBottomNavigation: Boolean = false

    private var userRated = false

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentExperienceFeedbackBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadImages()
        setComposeRatingContainer()
        setConfirmButtonText()
        setupObservers()
        setContinueButton()
    }

    private fun setupObservers() {
        viewModel.closeWindow.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
    }

    private fun preloadImages() {
        val glide = Glide.with(requireContext())
        setOf(
            R.drawable.img_feedback_0,
            R.drawable.img_feedback_1,
            R.drawable.img_feedback_2,
            R.drawable.img_feedback_3,
            R.drawable.img_feedback_4
        ).forEach {
            glide.load(it).preload()
        }
    }

    private fun setComposeRatingContainer() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            RadioGroupRating(
                backgroundColor = PrimaryMedium,
                onRatingSelected = {
                    viewModel.setUserRate(it.ordinal + 1)
                    userRated = true
                    setConfirmButtonText()
                }
            )
        }
    }

    private fun setConfirmButtonText() {
        binding.confirmButton.text = requireContext().getString(
            if (userRated) {
                R.string.send_feedback
            } else {
                R.string.skip
            }
        )
    }

    private fun setContinueButton() {
        binding.confirmButton.setOnClickListener {
            viewModel.onButtonPressed(args.sessionId, userRated)
        }
    }
}
