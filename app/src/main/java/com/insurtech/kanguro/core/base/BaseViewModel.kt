package com.insurtech.kanguro.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    @Inject
    protected lateinit var remoteConfigManager: IRemoteConfigManager

    // used to control the loaders on the view
    val isLoading = MutableLiveData(false)

    protected val _networkError =
        SingleLiveEvent<ErrorWithRetry<NetworkResponse.Error<*, ErrorDto>>>()
    val networkError: LiveData<ErrorWithRetry<NetworkResponse.Error<*, ErrorDto>>> = _networkError

    protected val _resultError =
        SingleLiveEvent<Pair<Result.Error, () -> Unit>>()
    val resultError: LiveData<Pair<Result.Error, () -> Unit>> = _resultError

    fun launchLoading(
        context: CoroutineContext,
        loadStateLiveData: MutableLiveData<Boolean> = isLoading,
        block: suspend CoroutineScope.() -> Unit
    ) {
        // if there is an operation already loading with the same loadState controller then do not execute
        if (loadStateLiveData.value == true) return
        loadStateLiveData.value = true
        viewModelScope.launch(context) {
            block.invoke(this)
            loadStateLiveData.postValue(false)
        }
    }

    fun getShouldShowRenters(): LiveData<Boolean> {
        val shouldShowRentersLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            shouldShowRentersLiveData.postValue(
                remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowRenters.key)
            )
        }
        return shouldShowRentersLiveData
    }

    fun getShouldShowLiveVetLiveData(): LiveData<Boolean> {
        val shouldShowLiveVet = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val shouldShow = getShouldShowLiveVet()
            shouldShowLiveVet.postValue(shouldShow)
        }
        return shouldShowLiveVet
    }

    suspend fun getShouldShowLiveVet(): Boolean {
        return remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldShowLiveVet.key)
    }
}
