package com.insurtech.kanguro.ui.scenes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageFilter
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.Reminder
import com.insurtech.kanguro.domain.model.ReminderType
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import com.insurtech.kanguro.ui.StartActivity
import com.insurtech.kanguro.ui.scenes.rentersDashboard.toUi
import com.insurtech.kanguro.usecase.IGetPolicyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.insurtech.kanguro.domain.model.PolicyStatus as DomainPolicyStatus

@HiltViewModel
class HomeDashboardViewModel(
    private val isFileClaim: Boolean,
    private val sessionManager: ISessionManager,
    private val userRepository: IRefactoredUserRepository,
    private val getPolicyUseCase: IGetPolicyUseCase,
    private val remoteConfigManager: IRemoteConfigManager
) : ViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        sessionManager: ISessionManager,
        userRepository: IRefactoredUserRepository,
        getPolicyUseCase: IGetPolicyUseCase,
        remoteConfigManager: IRemoteConfigManager
    ) : this(
        isFileClaim = savedStateHandle.get<Boolean>(StartActivity.IS_FILE_CLAIM) ?: false,
        sessionManager = sessionManager,
        userRepository = userRepository,
        getPolicyUseCase = getPolicyUseCase,
        remoteConfigManager = remoteConfigManager
    )

    data class HomeDashboardUiState(
        val userName: String = "",
        val petsNames: List<String> = emptyList(),
        val rentersCoverages: List<RentersCoverageSummaryCardModel> = emptyList(),
        val petsCoverages: List<PetsCoverageSummaryCardModel> = emptyList(),
        val reminders: List<ItemReminderUiModel> = emptyList(),
        val showRentersUpsellingBanner: Boolean = false,
        val showPetUpsellingBanner: Boolean = false,
        val showPetCoveragesFilter: Boolean = true,
        val showRentersCoveragesFilter: Boolean = false,
        val selectedPetCoverageFilter: CoverageFilter = CoverageFilter.Active,
        val selectedRentersCoverageFilter: CoverageFilter = CoverageFilter.Active,
        val showSelectFileAClaimTypeDialog: Boolean = false,
        val showRentersFileAClaimDialog: Boolean = false,
        val showLiveVeterinary: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    private val _homeDashboardState: MutableStateFlow<HomeDashboardUiState> =
        MutableStateFlow(HomeDashboardUiState())
    val uiState = _homeDashboardState.asStateFlow()

    private val _petsNeedAdditionalInfo = SingleLiveEvent<List<Pet>>()
    val petsNeedAdditionalInfo: LiveData<List<Pet>>
        get() = _petsNeedAdditionalInfo

    private val _petNeedsMedicalHistory = SingleLiveEvent<Long>()
    val petNeedsMedicalHistory: LiveData<Long>
        get() = _petNeedsMedicalHistory

    private val _userWantsFileClaim = SingleLiveEvent<Boolean>()
    val userWantsFileClaim: LiveData<Boolean>
        get() = _userWantsFileClaim

    private val _rentersOnboardingNeeded = SingleLiveEvent<String>()
    val rentersOnboardingNeeded: LiveData<String>
        get() = _rentersOnboardingNeeded

    private var petPolicies: List<PetPolicy> = emptyList()

    private var rentersPolicies: List<RentersPolicy> = emptyList()

    val userHasPets: Boolean
        get() = petPolicies.isNotEmpty()

    val userHasRenters: Boolean
        get() = rentersPolicies.isNotEmpty()

    val userAlreadyChoseCause: Boolean
        get() = sessionManager.sessionInfo?.donation != null

    private val _initialLoad = MutableLiveData(true)
    val initialLoad: LiveData<Boolean>
        get() = _initialLoad

    private var didInitialLoad = false

    private var _remindersList: List<Reminder> = emptyList()

    init {
        getUserName()
        fetchInformation()
    }

    fun fetchInformation() {
        _initialLoad.postValue(!didInitialLoad)

        viewModelScope.launch {
            didInitialLoad = true
            _homeDashboardState.update {
                copy(
                    isLoading = true,
                    isError = false
                )
            }

            val fetchRentersCoverage = async(Dispatchers.IO) { fetchRentersCoverages() }
            val fetchPetsCoverages = async(Dispatchers.IO) { fetchPetCoverages() }
            val fetchReminders = async(Dispatchers.IO) { fetchReminders() }
            val updateShouldShouLiveVet = async(Dispatchers.IO) { updateShouldShowLiveVet() }

            awaitAll(
                fetchRentersCoverage,
                fetchPetsCoverages,
                fetchReminders,
                updateShouldShouLiveVet
            )

            _initialLoad.postValue(false)

            _homeDashboardState.update {
                copy(isLoading = false)
            }

            checkIfOnboardingIsNeeded()
        }
    }

    private fun getUserName() {
        val userName = sessionManager.sessionInfo?.givenName ?: ""
        _homeDashboardState.update {
            copy(userName = userName)
        }
    }

    private suspend fun updateShouldShowLiveVet() {
        val shouldShowLiveVet =
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowLiveVet.key)
        _homeDashboardState.update {
            copy(showLiveVeterinary = shouldShowLiveVet)
        }
    }

    private fun checkIfOnboardingIsNeeded() {
        if (petsRequireAdditionalInfo(petPolicies)) return

        if (petsRequireMedicalHistory()) return

        checkIfRentersNeedOnboarding(rentersPolicies)
    }

    private suspend fun fetchPetCoverages() {
        val petPoliciesResult = getPolicyUseCase.fetchPetPoliciesResult()

        if (petPoliciesResult is Result.Success) {
            petPolicies = petPoliciesResult.data

            handlePetCoverageFilterChanged(CoverageFilter.All)
        } else {
            handleError()
        }
    }

    fun handlePetCoverageFilterChanged(filter: CoverageFilter) {
        val filteredPetPolicies = petPolicies.filter {
            when (filter) {
                CoverageFilter.Active -> it.status == PolicyStatus.ACTIVE
                CoverageFilter.Inactive ->
                    it.status == PolicyStatus.CANCELED ||
                        it.status == PolicyStatus.TERMINATED ||
                        it.status == PolicyStatus.PENDING

                CoverageFilter.All -> true
            }
        }

        updateUi(filteredPetPolicies)

        val distinctStatus = petPolicies.map { it.status }.distinctBy { it }
        val showFilter = distinctStatus.size > 1 && distinctStatus.any { it == PolicyStatus.ACTIVE }

        _homeDashboardState.update {
            copy(
                showPetCoveragesFilter = showFilter,
                selectedPetCoverageFilter = filter
            )
        }
    }

    private fun updateUi(policies: List<PetPolicy>) {
        val petsCoverageSummaryCardModels = policies.map { it.toPetsCoverageSummaryCardModel() }

        val petNames = petPolicies.map { it.pet?.name ?: "" }

        _homeDashboardState.update {
            copy(
                petsCoverages = petsCoverageSummaryCardModels,
                petsNames = petNames,
                showPetUpsellingBanner = petPolicies.isEmpty()
            )
        }
    }

    private fun petsRequireAdditionalInfo(petPolicies: List<PetPolicy>): Boolean {
        val petsWithNoAdditionalInfo =
            petPolicies.mapNotNull { it.pet }.filter { it.hasAdditionalInfo == false }

        if (petsWithNoAdditionalInfo.isNotEmpty()) {
            _petsNeedAdditionalInfo.postValue(petsWithNoAdditionalInfo)
            return true
        } else {
            _userWantsFileClaim.postValue(isFileClaim)
        }

        return false
    }

    private fun petsRequireMedicalHistory(): Boolean {
        val medicalHistoryReminders =
            _remindersList.filter { it.type == ReminderType.MedicalHistory }

        if (medicalHistoryReminders.isNotEmpty()) {
            val petId = if (medicalHistoryReminders.size == 1) {
                medicalHistoryReminders.first().pet.id ?: 0L
            } else {
                // In case of multiple pets, we use "0L" id to indicate the alert screen
                // to navigate to reminder screen
                0L
            }

            _petNeedsMedicalHistory.postValue(petId)
            return true
        }

        return false
    }

    private suspend fun fetchRentersCoverages() {
        when (val rentersCoveragesResult = getPolicyUseCase.fetchRentersPoliciesResult()) {
            is Result.Success -> {
                rentersPolicies = rentersCoveragesResult.data
                handleRentersCoverageFilterChanged(CoverageFilter.All)
            }

            is Result.Error -> {
                handleError()
            }
        }
    }

    private fun checkIfRentersNeedOnboarding(rentersPolicies: List<RentersPolicy>) {
        if (rentersPolicies.any { it.onboardingCompleted.not() }) {
            _rentersOnboardingNeeded.postValue(rentersPolicies.first { it.onboardingCompleted.not() }.id)
        }
    }

    fun handleRentersCoverageFilterChanged(filter: CoverageFilter) {
        val filteredRentersCoverages = rentersPolicies.filter {
            when (filter) {
                CoverageFilter.Active -> it.status == DomainPolicyStatus.ACTIVE
                CoverageFilter.Inactive ->
                    it.status == DomainPolicyStatus.CANCELED ||
                        it.status == DomainPolicyStatus.TERMINATED ||
                        it.status == DomainPolicyStatus.PENDING

                CoverageFilter.All -> true
            }
        }

        updateRentersUi(filteredRentersCoverages)

        val distinctStatus = rentersPolicies.map { it.status }.distinctBy { it }
        val showFilter =
            distinctStatus.size > 1 && distinctStatus.any { it == DomainPolicyStatus.ACTIVE }

        _homeDashboardState.update {
            copy(
                showRentersCoveragesFilter = showFilter,
                selectedRentersCoverageFilter = filter
            )
        }
    }

    private fun updateRentersUi(policies: List<RentersPolicy>) {
        val rentersCoverageSummaryCardModels = policies.map { it.toUi() }

        _homeDashboardState.update {
            copy(
                rentersCoverages = rentersCoverageSummaryCardModels,
                showRentersUpsellingBanner = rentersPolicies.isEmpty()
            )
        }
    }

    private suspend fun fetchReminders() {
        when (val remindersResult = userRepository.getReminders()) {
            is Result.Success -> {
                val reminders = remindersResult.data

                _remindersList = reminders
                val itemReminderUiModels = reminders.map { it.toItemReminderUiModel() }

                _homeDashboardState.update {
                    copy(reminders = itemReminderUiModels)
                }
            }

            is Result.Error -> {
                handleError()
            }
        }
    }

    private fun handleError() {
        _homeDashboardState.update {
            copy(isLoading = false, isError = true)
        }
    }

    fun getSelectedPetCoverage(policyId: String): PetPolicy? {
        return petPolicies.find { it.id == policyId }
    }

    fun handleFileAClaimPressed(showModal: Boolean) {
        _homeDashboardState.update {
            copy(showSelectFileAClaimTypeDialog = showModal)
        }
    }

    fun handleRentersFileAClaimPressed(showDialog: Boolean) {
        _homeDashboardState.update {
            copy(showRentersFileAClaimDialog = showDialog)
        }
    }
}
