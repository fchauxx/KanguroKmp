package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetForm

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredPetRepository
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.data.repository.IVeterinarianRepository
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.FormClaimType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.VetInformationUi
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.toPetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.toPetsInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.toVetsInformation
import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.Veterinarian
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetFormViewModel(
    private val userRepository: IRefactoredUserRepository,
    private val petsRepository: IRefactoredPetRepository,
    private val veterinarianRepository: IVeterinarianRepository,
    private val args: DirectPayToVetFormFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        userRepository: IRefactoredUserRepository,
        petsRepository: IRefactoredPetRepository,
        veterinarianRepository: IVeterinarianRepository,
        savedStateHandle: SavedStateHandle
    ) : this(
        userRepository,
        petsRepository,
        veterinarianRepository,
        DirectPayToVetFormFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    data class UiState(
        var isPetSelectorOpen: Boolean = false,
        var petList: List<PetInformation> = emptyList(),
        var petInformation: PetInformation? = null,
        var petPolicyName: String = "",
        var isVetSelectorOpen: Boolean = false,
        var veterinariansToSelect: List<VetInformationUi> = emptyList(),
        var vetSearchText: String = "",
        var isEditingVetInformationEnabled: Boolean = false,
        var veterinarianEmail: String? = null,
        var veterinarianName: String? = null,
        var clinicName: String? = null,
        var description: String = "",
        var isNextButtonEnabled: Boolean = false,
        var isVetEmailValid: Boolean = true,
        val isLoading: Boolean = true
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var pets: List<Pet>
    private lateinit var veterinarians: List<Veterinarian>

    private var selectedPet: Pet? = null
    private var selectedClaimType: ClaimType? = null
    private var selectedDate: Date? = null
    private var selectedVeterinarian: Veterinarian? = null
    private var description: String = ""
    private var veterinarianName: String = ""
    private var veterinarianEmail: String = ""
    private var veterinarianClinic: String = ""

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getUser()
            getPets()
            getVeterinarians()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private suspend fun getUser() {
        val result = userRepository.getUser()
        if (result is Result.Success) {
            _uiState.value = _uiState.value.copy(
                petPolicyName = result.data.givenName
            )
        } // TODO Handle Error
    }

    private suspend fun getPets() {
        val result = petsRepository.getUserPets()

        pets = if (result is Result.Success) {
            result.data
        } else {
            emptyList()
        }

        selectedPet = pets.firstOrNull()

        _uiState.value = _uiState.value.copy(
            petList = pets.toPetsInformation(),
            petInformation = selectedPet?.toPetInformation()
        )

        updateNextButtonState()
    }

    private suspend fun getVeterinarians() {
        val result = veterinarianRepository.getVeterinarians()
        veterinarians = if (result is Result.Success) {
            result.data
        } else {
            emptyList()
        }

        _uiState.value = _uiState.value.copy(
            veterinariansToSelect = veterinarians.toVetsInformation()
        )
    }

    fun setPetSelector(isOpen: Boolean) {
        _uiState.value = _uiState.value.copy(isPetSelectorOpen = isOpen)
    }

    fun handlePetSelected(petInformation: PetInformation) {
        selectedPet = pets.firstOrNull { it.id == petInformation.id }

        _uiState.value = _uiState.value.copy(
            petInformation = petInformation,
            isPetSelectorOpen = false
        )

        updateNextButtonState()
    }

    fun handleClaimTypeSelected(type: FormClaimType) {
        selectedClaimType = when (type) {
            FormClaimType.Accident -> ClaimType.Accident
            FormClaimType.Illness -> ClaimType.Illness
        }

        updateNextButtonState()
    }

    fun handleDateSelected(date: Date) {
        selectedDate = date
        updateNextButtonState()
    }

    fun setVetSelector(isOpen: Boolean) {
        _uiState.value = _uiState.value.copy(
            isPetSelectorOpen = false,
            isVetSelectorOpen = isOpen,
            veterinariansToSelect = emptyList(),
            vetSearchText = ""
        )
    }

    fun handleContinueEmailPressed(vetInformationUi: VetInformationUi) {
        val isEmailValid = _uiState.value.isVetEmailValid

        if (isEmailValid) {
            handleVetSelected(vetInformationUi)
            setVetSelector(false)
        }
    }

    fun handleVetSelected(vetInformationUi: VetInformationUi) {
        selectedVeterinarian = veterinarians.find { it.id == vetInformationUi.id }

        if (selectedVeterinarian != null) {
            _uiState.value = _uiState.value.copy(
                isEditingVetInformationEnabled = false,
                veterinarianName = vetInformationUi.name,
                clinicName = vetInformationUi.clinicName,
                veterinarianEmail = vetInformationUi.email
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isEditingVetInformationEnabled = true,
                veterinarianName = "",
                clinicName = "",
                veterinarianEmail = vetInformationUi.email
            )
        }

        veterinarianEmail = vetInformationUi.email ?: ""

        updateNextButtonState()
    }

    fun handleVetSearchTextChanged(text: String) {
        val filteredList = if (text.isNotBlank()) {
            veterinarians.filter {
                it.email.contains(text.trim(), ignoreCase = true)
            }.toVetsInformation()
        } else {
            emptyList()
        }

        val isEmailValid =
            Patterns.EMAIL_ADDRESS.matcher(text).matches()

        _uiState.value = _uiState.value.copy(
            vetSearchText = text,
            veterinariansToSelect = filteredList,
            isVetEmailValid = filteredList.isNotEmpty() || isEmailValid
        )
    }

    fun handleOnVeterinarianNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(
            veterinarianName = name
        )

        veterinarianName = name

        updateNextButtonState()
    }

    fun handleClinicNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(
            clinicName = name
        )

        veterinarianClinic = name

        updateNextButtonState()
    }

    fun handleDescriptionChanged(text: String) {
        description = text
        _uiState.value = _uiState.value.copy(
            description = text
        )
        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        _uiState.value =
            _uiState.value.copy(
                isNextButtonEnabled = selectedPet != null &&
                    selectedClaimType != null &&
                    selectedDate != null &&
                    isVetInformationValid() &&
                    description.isNotBlank()
            )
    }

    private fun isVetInformationValid(): Boolean {
        return selectedVeterinarian != null || (veterinarianName.isNotBlank() && veterinarianClinic.isNotBlank() && veterinarianEmail.isNotBlank())
    }

    fun getSharedFlow(): ClaimDirectPayment {
        return args.sharedFlow.copy(
            petId = selectedPet?.id?.toInt(),
            type = selectedClaimType,
            invoiceDate = selectedDate,
            description = description,
            veterinarianId = selectedVeterinarian?.id,
            veterinarianName = veterinarianName,
            veterinarianEmail = veterinarianEmail,
            veterinarianClinic = veterinarianClinic
        )
    }
}
