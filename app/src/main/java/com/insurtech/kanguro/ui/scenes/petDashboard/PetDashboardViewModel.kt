package com.insurtech.kanguro.ui.scenes.petDashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.IExternalLinksRepository
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import com.insurtech.kanguro.ui.scenes.home.toPetsCoverageSummaryCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDashboardViewModel @Inject constructor(
    private val petPolicyRepository: IRefactoredPolicyRepository,
    private val externalLinksRepository: IExternalLinksRepository,
    private val remoteConfigManager: IRemoteConfigManager,
    private val sessionManager: ISessionManager
) : ViewModel() {

    private val _state: MutableStateFlow<PetDashboardUiState> =
        MutableStateFlow(PetDashboardUiState())
    val state: StateFlow<PetDashboardUiState> get() = _state.asStateFlow()

    private var _petPolicyList: List<PetPolicy> = emptyList()

    private val _onOpenWebsite: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val onOpenWebsite: SharedFlow<String> = _onOpenWebsite.asSharedFlow()

    data class PetDashboardUiState(
        val coverages: SnapshotStateList<PetsCoverageSummaryCardModel> = mutableStateListOf(),
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val userHasPets: Boolean = false,
        val shouldShowLiveVet: Boolean = false
    )

    init {
        loadData()
    }

    fun loadData() {
        _state.update { copy(isLoading = true) }

        viewModelScope.launch {
            val petRequest = async { getPetCoverages() }
            val updateShouldShowLiveVetRequest = async { updateShouldShowLiveVet() }

            awaitAll(petRequest, updateShouldShowLiveVetRequest)

            _state.update { copy(isLoading = false) }
        }
    }

    private suspend fun getPetCoverages() {
        val petPolicies = petPolicyRepository.getPolicies()
        when (petPolicies) {
            is Result.Success -> {
                if (petPolicies.data.isNotEmpty()) {
                    _petPolicyList = petPolicies.data

                    _state.update {
                        copy(
                            coverages = petPolicies.data.map {
                                it.toPetsCoverageSummaryCardModel()
                            }.toMutableStateList(),
                            userHasPets = true
                        )
                    }
                } else {
                    _state.update { copy(userHasPets = false) }
                }
            }

            is Result.Error -> {
                _state.update { copy(isError = true) }
            }
        }
    }

    fun getAdvertiserWebsiteUrl(advertiserId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = sessionManager.sessionInfo?.id

            if (userId != null) {
                val externalLinkResult =
                    externalLinksRepository.getExternalLinks(advertiserId, userId)
                val url = externalLinkResult.data?.redirectTo

                if (url != null) {
                    _onOpenWebsite.emit(url)
                }
            }
        }
    }

    private suspend fun updateShouldShowLiveVet() {
        val shouldShowLiveVet =
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowLiveVet.key)
        _state.update { copy(shouldShowLiveVet = shouldShowLiveVet) }
    }

    fun getPetPolicy(policyId: String): PetPolicy? {
        return _petPolicyList.find { it.id == policyId }
    }
}
