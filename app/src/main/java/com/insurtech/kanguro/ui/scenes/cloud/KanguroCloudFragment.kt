package com.insurtech.kanguro.ui.scenes.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentKanguroCloudBinding
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KanguroCloudFragment : FullscreenFragment<FragmentKanguroCloudBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Cloud

    override val viewModel: KanguroCloudViewModel by activityViewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentKanguroCloudBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeShouldShowRenters()
        setupListeners()
        setupUi()
    }

    private fun setupUi() {
        binding.composeListView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeListView.setContent {
            val cloudState: KanguroCloudViewModel.CloudState by viewModel.cloudState.collectAsState()

            CloudItems(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = NeutralBackground),
                cloudPets = cloudState.pets,
                cloudRenters = cloudState.renters,
                isRefreshing = cloudState.isRefreshing,
                onPetSelected = ::handleSelectedPet,
                onRentersSelected = ::handleSelectedRenters,
                onPullToRefresh = viewModel::refreshDocuments
            )
        }
    }

    private fun handleSelectedPet(pet: CloudPet) {
        viewModel.selectCloud(pet.toSelectedCloud())
        findNavController().navigate(
            KanguroCloudFragmentDirections.actionKanguroCloudFragmentToCloudPetFragment()
        )
    }

    private fun handleSelectedRenters(renters: CloudRenters) {
        viewModel.selectCloud(renters.toSelectedCloud())
        findNavController().navigate(
            KanguroCloudFragmentDirections.actionKanguroCloudFragmentToCloudPetFragment()
        )
    }

    private fun setupListeners() {
        binding.backText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeShouldShowRenters() {
        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) { shouldShowRenters ->
            val elementFolderLevelLayoutParams =
                binding.folderLevel.layoutParams as ConstraintLayout.LayoutParams

            elementFolderLevelLayoutParams.topToBottom =
                if (shouldShowRenters) R.id.toolbar else R.id.title

            binding.title.isGone = shouldShowRenters
            binding.toolbar.isGone = !shouldShowRenters
        }
    }
}
