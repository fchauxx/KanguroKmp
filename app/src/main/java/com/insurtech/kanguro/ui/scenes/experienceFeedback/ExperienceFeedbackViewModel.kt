package com.insurtech.kanguro.ui.scenes.experienceFeedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ExperienceFeedbackViewModel @Inject constructor(
    private val refactoredClaimRepository: IRefactoredClaimRepository
) : BaseViewModel() {

    val label = MutableLiveData<Int>()
    val image = MutableLiveData<Int>()
    val feedback = MutableLiveData<String>()
    private var selectedRating = INITIAL_RATING_VALUE

    private val _closeWindow = MutableLiveData<Boolean>()
    val closeWindow: LiveData<Boolean> = _closeWindow

    init {
        setUserRate(INITIAL_RATING_VALUE)
    }

    fun setUserRate(value: Int) {
        selectedRating = value
        when (value) {
            1 -> {
                label.value = R.string.very_bad
                image.value = R.drawable.img_feedback_0
            }

            2 -> {
                label.value = R.string.bad
                image.value = R.drawable.img_feedback_1
            }

            3 -> {
                label.value = R.string.ok
                image.value = R.drawable.img_feedback_2
            }

            4 -> {
                label.value = R.string.good
                image.value = R.drawable.img_feedback_3
            }

            else -> {
                label.value = R.string.amazing
                image.value = R.drawable.img_feedback_4
            }
        }
    }

    fun onButtonPressed(claimId: String, userRated: Boolean) {
        if (userRated.not()) {
            _closeWindow.postValue(true)
            return
        }

        launchLoading(Dispatchers.IO) {
            val putClaimFeedbackOperationResult = refactoredClaimRepository.putClaimFeedback(
                claimId = claimId,
                rating = selectedRating,
                description = feedback.value
            )

            val isSuccessfulPutClaimFeedbackOperation = putClaimFeedbackOperationResult.data

            if (isSuccessfulPutClaimFeedbackOperation != null && isSuccessfulPutClaimFeedbackOperation) {
                _closeWindow.postValue(true)
            } else {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        onButtonPressed(claimId, userRated)
                    }
                )
            }
        }
    }

    companion object {
        private const val INITIAL_RATING_VALUE = 3
    }
}
