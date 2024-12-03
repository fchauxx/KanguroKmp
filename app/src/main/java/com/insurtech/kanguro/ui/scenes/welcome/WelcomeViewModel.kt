package com.insurtech.kanguro.ui.scenes.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IBackendVersionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val backendVersionRepository: IBackendVersionRepository
) : BaseViewModel() {

    private val _isAppUpdated = MutableLiveData<Boolean>()
    val isAppUpdated: LiveData<Boolean>
        get() = _isAppUpdated

    init {
        checkBackendVersion()
    }

    private fun checkBackendVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            backendVersionRepository.getBackendVersion()
                .catch { e -> Result.Error(Exception(e)) }
                .collect { backendVersionResult ->
                    if (backendVersionResult is Result.Success) {
                        val backendVersion = backendVersionResult.data.version.split(".")
                        val backendVersionToInt = backendVersion[0].toIntOrNull()

                        if (backendVersionToInt != null && backendVersionToInt <= BuildConfig.SUPPORTED_BACKEND_VERSION) {
                            _isAppUpdated.postValue(true)
                        } else {
                            _isAppUpdated.postValue(false)
                        }
                    }
                }
        }
    }
}
