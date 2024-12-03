package com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.insurtech.kanguro.core.session.ISessionManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

abstract class PledgeOfHonorBaseViewModel(
    private val sessionManager: ISessionManager
) : ViewModel() {

    val isSigned = MutableLiveData(false)
    val isLoading = MutableLiveData(false)
    val supportedCauseTitle = sessionManager.sessionInfo?.donation?.title

    init {
        getNameAndDate()
    }

    @SuppressLint("SimpleDateFormat")
    fun getNameAndDate(): String {
        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("MMM dd, yyyy ", Locale.getDefault())
        return "${sessionManager.sessionInfo?.givenName} ${sessionManager.sessionInfo?.surname}, ${dateTimeFormat.format(date)}"
    }

    fun toggleButton(value: Boolean) {
        isSigned.value = value
    }
}
