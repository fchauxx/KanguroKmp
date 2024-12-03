package com.insurtech.kanguro.ui.scenes.supportCause

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.data.repository.ICharityRepository
import com.insurtech.kanguro.domain.model.Charity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import com.insurtech.kanguro.data.Result.Error as ResultError
import com.insurtech.kanguro.data.Result.Success as ResultSuccess

@HiltViewModel
class ChangeCauseViewModel @Inject constructor(
    private val sessionManager: ISessionManager,
    private val charityRepository: ICharityRepository
) : BaseViewModel() {

    private val _cause = MutableLiveData<Charity>()
    val cause: LiveData<Charity>
        get() = _cause

    fun getCauseInfo() {
        val supportedCause = sessionManager.sessionInfo?.donation

        launchLoading(Dispatchers.IO) {
            when (val result = charityRepository.getAll()) {
                is ResultSuccess -> {
                    result.data.firstOrNull {
                        it.attributes?.charityKey == supportedCause?.charityId
                    }?.let {
                        _cause.postValue(it)
                    }
                }
                is ResultError -> {
                    val error = Pair(result, ::getCauseInfo)
                    _resultError.postValue(error)
                }
            }
        }
    }
}
