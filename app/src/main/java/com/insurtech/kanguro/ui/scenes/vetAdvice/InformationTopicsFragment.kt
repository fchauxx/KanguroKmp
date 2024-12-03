package com.insurtech.kanguro.ui.scenes.vetAdvice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.common.enums.KanguroParameterType
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentInformationTopicsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationTopicsFragment : FullscreenFragment<FragmentInformationTopicsBinding>() {

    override val viewModel: InformationsTopicsViewModel by viewModels()

    private val args: InformationTopicsFragmentArgs by navArgs()

    override val screenName: AnalyticsEnums.Screen
        get() = when (args.topicReferred) {
            InformationScreenType.FAQ -> AnalyticsEnums.Screen.FrequentlyAskedQuestions
            InformationScreenType.NewPetParent -> AnalyticsEnums.Screen.NewPetParents
            InformationScreenType.Vet_Advice_Dog -> AnalyticsEnums.Screen.VetAdvice
            InformationScreenType.Vet_Advice_Cat -> AnalyticsEnums.Screen.VetAdvice
        }

    private val advicesAdapter by lazy {
        VetAdvicesAdapter()
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentInformationTopicsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButton()
        setInformations()
        setupListeners()
    }

    override fun processWindowInsets(insets: Insets) {
        binding.returnButtonFrame.updatePadding(top = insets.top)
    }

    private fun setupListeners() {
        viewModel.advicesLoad.observe(viewLifecycleOwner) { advicesList ->
            advicesAdapter.submitList(advicesList)
        }
    }

    private fun setBackButton() {
        binding.returnButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setInformations() {
        // TODO Make the sealed class for the type of advice
        val headerImage = binding.headerImage
        val headerAdapter: RecyclerView.Adapter<*>
        when (args.topicReferred) {
            InformationScreenType.FAQ -> {
                headerImage.setImageResource(R.drawable.frequently_asked_questions)
                viewModel.getAdvices(InformationTopics.FAQ)
                headerAdapter = FrequentlyAskedQuestionsAdapter()
            }
            InformationScreenType.NewPetParent -> {
                headerImage.setImageResource(R.drawable.new_pet_parent)
                viewModel.getAdvices(InformationTopics.NewPetParent)
                headerAdapter = DoctorCardAdapter(
                    getString(R.string.new_parents_label),
                    getString(R.string.new_pet_parents)
                )
            }
            InformationScreenType.Vet_Advice_Dog -> {
                headerImage.setImageResource(R.drawable.dog_banner)
                viewModel.getAdvices(InformationTopics.VetAdvice, KanguroParameterType.VA_D)
                headerAdapter = DoctorCardAdapter(
                    getString(R.string.dogs_label),
                    getString(R.string.vet_advice_for_dogs)
                )
            }
            InformationScreenType.Vet_Advice_Cat -> {
                headerImage.setImageResource(R.drawable.cat_banner)
                viewModel.getAdvices(InformationTopics.VetAdvice, KanguroParameterType.VA_C)
                headerAdapter = DoctorCardAdapter(
                    getString(R.string.cats_label),
                    getString(R.string.vet_advice_for_cats)
                )
            }
        }
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, advicesAdapter)
    }
}
