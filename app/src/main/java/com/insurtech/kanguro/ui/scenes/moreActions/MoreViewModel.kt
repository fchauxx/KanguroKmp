package com.insurtech.kanguro.ui.scenes.moreActions

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import com.insurtech.kanguro.usecase.IGetPolicyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val fileManager: KanguroFileManager,
    private val remoteConfigManager: IRemoteConfigManager,
    private val getPolicyUseCase: IGetPolicyUseCase
) : ViewModel() {

    private val _fetchingUseTerms = MutableLiveData<Boolean>()
    val fetchingUseTerms: LiveData<Boolean> = _fetchingUseTerms

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val openUseTermsEvent = SingleLiveEvent<Uri?>()

    private val _userHasPets = MutableLiveData<Boolean>(false)
    val userHasPets: LiveData<Boolean> = _userHasPets

    private val _shouldShowRenters = MutableLiveData<Boolean>(false)
    val shouldShowRenters: LiveData<Boolean> = _shouldShowRenters

    private val _shouldShowLiveVet = MutableLiveData<Boolean>(false)
    val shouldShowLiveVet: LiveData<Boolean> = _shouldShowLiveVet

    init {
        loadData()
    }

    fun loadData() {
        _isLoading.value = true

        viewModelScope.launch {
            userHasPet()
            val shouldShowRentersJob = async { getShouldShowRenters() }
            val shouldShowLiveVetJob = async { getShouldShowLiveVet() }

            awaitAll(shouldShowRentersJob, shouldShowLiveVetJob)

            _isLoading.value = false
        }
    }

    fun openUseTermsPressed() {
        if (fetchingUseTerms.value == true) return

        _fetchingUseTerms.value = true
        viewModelScope.launch {
            openUseTermsEvent.postValue(fileManager.getUseTermsFile())
            _fetchingUseTerms.postValue(false)
        }
    }

    private suspend fun getShouldShowRenters() {
        _shouldShowRenters.postValue(
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowRenters.key)
        )
    }

    private suspend fun getShouldShowLiveVet() {
        _shouldShowLiveVet.postValue(
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowLiveVet.key)
        )
    }

    private fun userHasPet() {
        val petPolicies = getPolicyUseCase.getPetPolicies()
        _userHasPets.value = petPolicies.isNotEmpty()
    }
}
