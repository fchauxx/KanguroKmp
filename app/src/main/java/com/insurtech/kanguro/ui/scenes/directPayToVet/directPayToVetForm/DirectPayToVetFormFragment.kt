package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetFormBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm.DirectPayToVetFormScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm.DirectPayToVetFormScreenLoaderState
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetType
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetFormFragment : KanguroBottomSheetFragment<FragmentDirectPayToVetFormBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.DirectPayToVetForm

    override val viewModel: DirectPayToVetFormViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetFormBinding =
        FragmentDirectPayToVetFormBinding.inflate(inflater)

    override val isDraggable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun onNextPressed() {
        findNavController()
            .safeNavigate(
                DirectPayToVetFormFragmentDirections
                    .actionDirectPayToVetFormFragmentToDirectPayToVetPledgeOfHonorBottomSheet(
                        viewModel.getSharedFlow()
                    )
            )
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            DirectPayToVetFormFragment(
                viewModel = viewModel,
                onClosePressed = { onCloseButtonPressed() },
                onBackButtonPressed = { dismiss() },
                onNextPressed = { onNextPressed() }
            )
        }
    }

    private fun onCloseButtonPressed() {
        findNavController().popBackStack(R.id.directPayToVetInitFlowFragment, true)
    }
}

@Composable
private fun DirectPayToVetFormFragment(
    viewModel: DirectPayToVetFormViewModel = viewModel(),
    onClosePressed: () -> Unit,
    onBackButtonPressed: () -> Unit,
    onNextPressed: () -> Unit
) {
    val uiState: DirectPayToVetFormViewModel.UiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        DirectPayToVetFormScreenLoaderState(
            onClosePressed = onClosePressed,
            onBackButtonPressed = onBackButtonPressed
        )
    } else {
        DirectPayToVetFormScreenContent(
            onClosePressed = onClosePressed,
            onBackButtonPressed = onBackButtonPressed,
            onNamePetClick = { viewModel.setPetSelector(true) },
            petInformation = uiState.petInformation,
            policyName = uiState.petPolicyName,
            isPetSelectorOpen = uiState.isPetSelectorOpen,
            onPetSelectorDismiss = { viewModel.setPetSelector(false) },
            onPetSelected = {
                viewModel.handlePetSelected(it)
                viewModel.setPetSelector(false)
            },
            petList = uiState.petList,
            onClaimTypeSelected = { viewModel.handleClaimTypeSelected(it) },
            onDateSelected = { viewModel.handleDateSelected(it) },
            description = uiState.description,
            onDescriptionChanged = { viewModel.handleDescriptionChanged(it) },
            onVetEmailPressed = { viewModel.setVetSelector(true) },
            allVetsInformation = uiState.veterinariansToSelect,
            isVetSelectorOpen = uiState.isVetSelectorOpen,
            onVetSelected = {
                viewModel.handleVetSelected(it)
                viewModel.setVetSelector(false)
            },
            onVetSelectorDismiss = { viewModel.setVetSelector(false) },
            vetSearchText = uiState.vetSearchText,
            onVetSearchTextChanged = { viewModel.handleVetSearchTextChanged(it) },
            isEditingVetInformationEnabled = uiState.isEditingVetInformationEnabled,
            veterinarianEmail = uiState.veterinarianEmail ?: "",
            veterinarianName = uiState.veterinarianName ?: "",
            onVeterinarianNameChanged = { viewModel.handleOnVeterinarianNameChanged(it) },
            clinicName = uiState.clinicName ?: "",
            onClinicNameChanged = { viewModel.handleClinicNameChanged(it) },
            isNextButtonEnabled = uiState.isNextButtonEnabled,
            onNextPressed = onNextPressed,
            isVetEmailValid = uiState.isVetEmailValid,
            onContinueEmailButtonClick = {
                viewModel.handleContinueEmailPressed(it)
            }
        )
    }
}

@Preview
@Composable
private fun DirectPayToVetFormFragmentPreview() {
    Surface {
        DirectPayToVetFormScreenContent(
            onClosePressed = {},
            onBackButtonPressed = {},
            onNamePetClick = {},
            petInformation = PetInformation(
                1L,
                "Oliver",
                PetType.dog
            ),
            policyName = "Lauren Ipsum",
            isPetSelectorOpen = false,
            onPetSelectorDismiss = {},
            onPetSelected = {},
            petList = listOf(
                PetInformation(1L, "Oliver", PetType.dog),
                PetInformation(2L, "Mimi", PetType.cat)
            ),
            onClaimTypeSelected = {},
            onDateSelected = {},
            description = "",
            onDescriptionChanged = {},
            onVetEmailPressed = {},
            allVetsInformation = emptyList(),
            isVetSelectorOpen = false,
            onVetSelected = { },
            onVetSelectorDismiss = {},
            vetSearchText = "",
            onVetSearchTextChanged = {},
            isEditingVetInformationEnabled = false,
            veterinarianName = "Javier the Vet",
            onVeterinarianNameChanged = {},
            clinicName = "Kanguro",
            onClinicNameChanged = {},
            isNextButtonEnabled = false,
            onNextPressed = {},
            isVetEmailValid = true,
            onContinueEmailButtonClick = {}
        )
    }
}
