package com.insurtech.kanguro.ui.scenes.referFriends

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReferFriendsViewModel @Inject constructor(
    private val fileManager: KanguroFileManager
) : ViewModel() {

    private val _fetchingUseTerms = MutableLiveData<Boolean>()
    val fetchingUseTerms: LiveData<Boolean> = _fetchingUseTerms

    val openUseTermsEvent = SingleLiveEvent<Uri?>()

    fun openUseTermsPressed() {
        if (fetchingUseTerms.value == true) return

        _fetchingUseTerms.value = true
        viewModelScope.launch {
            openUseTermsEvent.postValue(fileManager.getUseTermsFile())
            _fetchingUseTerms.postValue(false)
        }
    }
}
