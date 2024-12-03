package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetPledgeOfHonor

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.BitmapUtils
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase.PledgeOfHonorBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetPledgeOfHonorViewModel(
    sessionManager: ISessionManager,
    private val args: DirectPayToVetPledgeOfHonorBottomSheetArgs,
    private val claimRepository: IRefactoredClaimRepository
) : PledgeOfHonorBaseViewModel(sessionManager) {

    @Inject
    constructor(
        sessionManager: ISessionManager,
        savedStateHandle: SavedStateHandle,
        claimRepository: IRefactoredClaimRepository
    ) : this(
        sessionManager,
        DirectPayToVetPledgeOfHonorBottomSheetArgs.fromSavedStateHandle(savedStateHandle),
        claimRepository
    )

    val isButtonEnable = MediatorLiveData<Boolean>().apply {
        addSource(isSigned) { checkButtonEnabled() }
    }

    val claimInformationResponse: SingleLiveEvent<String?> = SingleLiveEvent()

    private fun checkButtonEnabled() {
        isButtonEnable.value = isSigned.value == true
    }

    fun handleSignature(
        image: Bitmap
    ) {
        val resizedImage = BitmapUtils.resizeBitmap(image, 1500) ?: return

        ByteArrayOutputStream().use {
            resizedImage.compress(Bitmap.CompressFormat.PNG, 90, it)
            sendFileReply(it.toByteArray(), ".png")
        }
    }

    private fun sendFileReply(
        fileData: ByteArray,
        extension: String
    ) {
        val imageStr = Base64.encodeToString(fileData, Base64.DEFAULT)
        val claimDirectPayment = args.sharedFlow.copy(
            pledgeOfHonor = imageStr,
            pledgeOfHonorExtension = extension
        )

        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)

            val result = claimRepository.postDirectPayment(claimDirectPayment)
            if (result is Result.Success) {
                claimInformationResponse.postValue(result.data.id)
            } else {
                claimInformationResponse.postValue(null)
            }

            isLoading.postValue(false)
        }
    }
}
