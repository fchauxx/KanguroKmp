package com.insurtech.kanguro.ui.scenes.supportCause

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.common.enums.CharityCause
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result.Error
import com.insurtech.kanguro.data.Result.Success
import com.insurtech.kanguro.data.repository.ICharityRepository
import com.insurtech.kanguro.domain.model.Charity
import com.insurtech.kanguro.domain.model.Donation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportCauseViewModel @Inject constructor(
    private val sessionManager: ISessionManager,
    private val charityRepository: ICharityRepository
) : BaseViewModel() {
    private var charityList: List<Charity> = arrayListOf()

    private val _filteredCharityList = MutableLiveData<List<CharityListItem>>()
    val filteredCharityList: LiveData<List<CharityListItem>>
        get() = _filteredCharityList

    val donationDone = SingleLiveEvent<Boolean>()

    var selectedCause: CharityCause? = null

    fun fetchCharityList() {
        launchLoading(Dispatchers.IO) {
            when (val result = charityRepository.getAll()) {
                is Success -> {
                    charityList = result.data
                }
                is Error -> {
                    val error = Pair(result, ::fetchCharityList)
                    _resultError.postValue(error)
                }
            }
        }
    }

    fun getFilteredCharityList() {
        val filteredList = charityList.filter { it.attributes?.cause == selectedCause }
            .map { charityResponse ->
                CharityListItem(charityResponse)
            }
        _filteredCharityList.postValue(filteredList)
    }

    fun onCauseSelected(cause: CharityCause) {
        this.selectedCause = cause
    }

    fun onCharitySelected(charityResponse: Charity) {
        val userId = sessionManager.sessionInfo?.id

        if (userId != null) {
            val donation = Donation(
                userId,
                charityResponse.attributes?.charityKey,
                charityResponse.attributes?.title,
                charityResponse.attributes?.cause
            )
            viewModelScope.launch {
                when (val result = charityRepository.syncUserDonation(donation)) {
                    is Success -> {
                        donationDone.postValue(true)
                        sessionManager.sessionInfo =
                            sessionManager.sessionInfo?.copy(donation = donation)
                    }
                    is Error -> {
                        _resultError.postValue(
                            Pair(result, ::getFilteredCharityList)
                        )
                    }
                }
            }
        }
    }
}
