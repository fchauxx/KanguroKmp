package com.insurtech.kanguro.ui.scenes.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.google.firebase.messaging.FirebaseMessaging
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.core.api.bodies.TokenBody
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.pets.IPetsRepository
import com.insurtech.kanguro.core.repository.policy.IPolicyRepository
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.IBackendVersionRepository
import com.insurtech.kanguro.data.repository.IExternalLinksRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.domain.dashboard.CoveragesFilter
import com.insurtech.kanguro.domain.dashboard.LastActivity
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import com.insurtech.kanguro.ui.StartActivity.Companion.IS_FILE_CLAIM
import com.insurtech.kanguro.ui.scenes.home.toPetsCoverageSummaryCardModel
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    private val backendVersionRepository: IBackendVersionRepository,
    private val petsService: IPetsRepository,
    private val userService: IUserRepository,
    private val policyRepository: IPolicyRepository,
    private val preferences: SharedPreferences,
    private val externalLinksRepository: IExternalLinksRepository
) : BaseViewModel() {

    sealed class State {
        object Data : State()
        object Loading : State()
        object Upselling : State()
        object Error : State()
    }

    private val _petsList = MutableLiveData<List<String>>()
    val petsList: LiveData<List<String>>
        get() = _petsList

    private val _coveragesList = MutableLiveData<List<PetPolicy>>()
    val coveragesList: LiveData<List<PetPolicy>>
        get() = _coveragesList

    private val _petsCardList = MutableLiveData<List<PetsCoverageSummaryCardModel>>()
    val petsCardList: LiveData<List<PetsCoverageSummaryCardModel>>
        get() = _petsCardList

    private val _filteredCoveragesList = MutableLiveData<List<PetPolicy>>()
    val filteredCoveragesList: LiveData<List<PetPolicy>>
        get() = _filteredCoveragesList

    private val _remindersList = MutableLiveData<List<Reminder>>()
    val remindersList: LiveData<List<Reminder>>
        get() = _remindersList

    private val _activityList = MutableLiveData<List<LastActivity>>()
    val activityList: LiveData<List<LastActivity>>
        get() = _activityList

    private val _initialLoad = MutableLiveData(true)
    val initialLoad: LiveData<Boolean>
        get() = _initialLoad

    private val _mainNavigationAction = SingleLiveEvent<NavDirections>()
    val mainNavigationAction: LiveData<NavDirections> = _mainNavigationAction

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private var _isAppUpdated = MutableLiveData<Boolean>()
    val isAppUpdated: LiveData<Boolean>
        get() = _isAppUpdated

    private var didInitialLoad = false

    val isAccountBlocked = SingleLiveEvent<Boolean>()

    private val _coveragesState = MutableLiveData<State>(State.Data)
    val coveragesState: LiveData<State>
        get() = _coveragesState

    private val _paymentState = MutableLiveData<State>(State.Data)
    val paymentState: LiveData<State>
        get() = _paymentState

    private val _remindersState = MutableLiveData<State>(State.Data)
    val remindersState: LiveData<State>
        get() = _remindersState

    private val _updateAppLanguage = SingleLiveEvent<AppLanguage?>()

    val updateAppLanguage: LiveData<AppLanguage?>
        get() = _updateAppLanguage

    val onOpenRoamWebsite = SingleLiveEvent<String?>()

    private var isFileClaim = savedStateHandle.get<Boolean>(IS_FILE_CLAIM) ?: false

    private val _isCoveragesLoading = MutableStateFlow<Boolean>(false)
    val isCoveragesLoading = _isCoveragesLoading.asStateFlow()

    private val _isPaymentSettingsLoading = MutableStateFlow<Boolean>(false)
    val isPaymentSettingsLoading = _isPaymentSettingsLoading.asStateFlow()

    private val _isRemindersLoading = MutableStateFlow<Boolean>(false)
    val isRemindersLoading = _isRemindersLoading.asStateFlow()

    var currentScreen: AnalyticsEnums.Screen? = null

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        checkBackendVersion()
        sendToken()
    }

    private fun checkBackendVersion() {
        _initialLoad.postValue(!didInitialLoad)

        viewModelScope.launch(Dispatchers.IO) {
            backendVersionRepository.getBackendVersion()
                .catch { e -> Result.Error(Exception(e)) }
                .collect { backendVersionResult ->
                    if (backendVersionResult is Result.Success) {
                        val backendVersion = backendVersionResult.data.version.split(".")
                        val backendVersionToInt = backendVersion[0].toIntOrNull()

                        if (backendVersionToInt != null && backendVersionToInt <= BuildConfig.SUPPORTED_BACKEND_VERSION) {
                            _isAppUpdated.postValue(true)
                            val showRenters =
                                remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowRenters.key)
                            if (!showRenters) {
                                fetchCoveragesAndPets()
                                fetchUserReminders()
                            } else {
                                _initialLoad.postValue(false)
                                _isRefreshing.postValue(false)
                            }
                            getUpdatedUser()
                            didInitialLoad = true
                        } else {
                            _isAppUpdated.postValue(false)
                            _initialLoad.postValue(false)
                        }
                    } else {
                        val errorResult = backendVersionResult as Result.Error

                        _networkError.postValue(
                            ErrorWithRetry(
                                NetworkResponse.UnknownError<Unit, ErrorDto>(
                                    errorResult.exception,
                                    null
                                ),
                                ::fetchDashboard
                            )
                        )
                        _initialLoad.postValue(false)
                    }
                }
        }
    }

    private fun getUpdatedUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userService.getUser().onSuccess {
                val selectedLanguage = body.language
                if (selectedLanguage != null && selectedLanguage.language != preferences.preferredLanguage?.language) {
                    _updateAppLanguage.postValue(selectedLanguage)
                }
            }.onError {
                _networkError.postValue(ErrorWithRetry(this, ::getUpdatedUser))
            }
        }
    }

    private fun fetchCoveragesAndPets() {
        if (didInitialLoad) {
            _isRefreshing.postValue(true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val policies = async { fetchPolicies() }
            val additionalInfos = async { checkRequiresAdditionalInfos() }
            policies.await()
            additionalInfos.await()
            _initialLoad.postValue(false)
            _isRefreshing.postValue(false)
        }
    }

    private suspend fun checkRequiresAdditionalInfos() {
        petsService.getUserPets().onSuccess {
            val requireAdditionalInfos = body.filter {
                it.hasAdditionalInfo == false
            }

            _petsList.postValue(body.mapNotNull { it.name })

            if (requireAdditionalInfos.isNotEmpty()) {
                _mainNavigationAction.postValue(
                    DashboardFragmentDirections.actionDashboardFragmentToChatBotFragment(
                        requireAdditionalInfos.toTypedArray()
                    )
                )
            } else if (isFileClaim) {
                _mainNavigationAction.postValue(
                    DashboardFragmentDirections.actionGlobalJavierChatbotFragment(
                        ChatbotType.NewClaim(
                            null
                        )
                    )
                )
            }
            isFileClaim = false
        }
    }

    private suspend fun fetchPolicies() {
        policyRepository.getPolicies().onSuccess {
            val petPolicies = this.body

            _coveragesList.postValue(petPolicies)

            _petsCardList.postValue(
                petPolicies.map {
                    it.toPetsCoverageSummaryCardModel()
                }
            )

            if (petPolicies.isEmpty()) {
                _coveragesState.postValue(State.Upselling)
            } else {
                _coveragesState.postValue(State.Data)
            }

            _paymentState.postValue(State.Data)
        }.onError {
            onPolicyError(this)
        }
    }

    fun getSelectedPetCoverage(policyId: String): PetPolicy? {
        return _coveragesList.value?.find { it.id == policyId }
    }

    private suspend fun onPolicyError(error: NetworkResponse.Error<List<PetPolicy>, ErrorDto>) {
        val isRenters =
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowRenters.key)

        when {
            currentScreen == AnalyticsEnums.Screen.Coverages && isRenters -> {
                _coveragesState.postValue(State.Error)
            }

            else -> {
                _paymentState.postValue(State.Data)
                _coveragesState.postValue(State.Data)
                _networkError.postValue(
                    ErrorWithRetry(error) {
                        viewModelScope.launch(Dispatchers.IO) { fetchPolicies() }
                    }
                )
            }
        }
    }

    fun refreshPolicies() {
        viewModelScope.launch(Dispatchers.IO) { fetchPolicies() }
    }

    fun coveragesRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isCoveragesLoading.update { true }
            _coveragesState.postValue(State.Loading)
            fetchPolicies()
            _isCoveragesLoading.update { false }
        }
    }

    fun paymentSettingsRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isPaymentSettingsLoading.update { true }
            _paymentState.postValue(State.Loading)
            fetchPolicies()
            _isPaymentSettingsLoading.update { false }
        }
    }

    fun fetchUserReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            userService.getReminders().onSuccess {
                val reminders = this.body
                _remindersList.postValue(reminders)
                checkRequiredMedicalHistory(reminders)
            }.onError {
                _networkError.postValue(ErrorWithRetry(this, ::fetchUserReminders))
            }
        }
    }

    fun remindersRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRemindersLoading.update { true }
            _remindersState.postValue(State.Loading)
            userService.getReminders().onSuccess {
                val reminders = this.body
                _remindersList.postValue(reminders)
                checkRequiredMedicalHistory(reminders)
            }.onError {
                _networkError.postValue(ErrorWithRetry(this, ::fetchUserReminders))
            }
            _remindersState.postValue(State.Data)
            _isRemindersLoading.update { false }
        }
    }

    private fun checkRequiredMedicalHistory(reminders: List<Reminder>) {
        val medicalHistoryReminders =
            reminders.filter { it.type == ReminderType.MedicalHistory }

        if (medicalHistoryReminders.isNotEmpty()) {
            val petId = if (medicalHistoryReminders.size == 1) {
                medicalHistoryReminders.first().pet.id ?: 0L
            } else {
                // In case of multiple pets, we use "0L" id to indicate the alert screen
                // to navigate to reminder screen
                0L
            }

            _mainNavigationAction.postValue(
                DashboardFragmentDirections.actionGlobalMedicalHistoryAlertDialog(
                    petId
                )
            )
        }
    }

    @SuppressLint("HardwareIds")
    fun sendToken() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }

                val firebaseToken = task.result

                val androidId = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )

                val token = TokenBody(
                    firebaseToken,
                    androidId
                )

                viewModelScope.launch {
                    userService.putUserFirebaseToken(token)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun filterCoverages(filter: CoveragesFilter) {
        val listFiltered =
            when (filter) {
                CoveragesFilter.Active -> _coveragesList.value?.filter {
                    it.status == PolicyStatus.ACTIVE
                }

                CoveragesFilter.Inactive -> _coveragesList.value?.filter {
                    it.status == PolicyStatus.CANCELED || it.status == PolicyStatus.TERMINATED
                }

                else -> _coveragesList.value
            }
        _filteredCoveragesList.postValue(listFiltered ?: emptyList())
    }

    fun userHasAccountBlocked(userId: String) {
        launchLoading(Dispatchers.IO) {
            userService.getHasAccessBlocked(userId).onSuccess {
                val isBlocked = this.body
                isAccountBlocked.postValue(isBlocked)
            }.onError {
                _networkError.postValue(
                    ErrorWithRetry(this) {
                        userHasAccountBlocked(userId)
                    }
                )
            }
        }
    }

    fun onClickRoamAdvertising(advertiserId: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val externalLinkResult = externalLinksRepository.getExternalLinks(advertiserId, userId)
            val url = externalLinkResult.data?.redirectTo

            onOpenRoamWebsite.postValue(url)
        }
    }
}
