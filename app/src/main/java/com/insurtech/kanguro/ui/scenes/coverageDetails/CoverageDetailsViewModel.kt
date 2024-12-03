package com.insurtech.kanguro.ui.scenes.coverageDetails

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.api.bodies.PetPictureBody
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.repository.pets.PetsRepository
import com.insurtech.kanguro.core.repository.policy.IPolicyRepository
import com.insurtech.kanguro.core.utils.GlideUrlWithParameters
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.core.utils.getCacheableImage
import com.insurtech.kanguro.core.utils.getFormattedBreedAndAge
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.pet.PictureBase64
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoverageDetailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val policyRepository: IPolicyRepository,
    private val petsRepository: PetsRepository,
    private val fileManager: KanguroFileManager
) : BaseViewModel() {

    private val _coverage = MutableLiveData<PetPolicy>()
    val coverage: LiveData<PetPolicy>
        get() = _coverage

    private val _policyDocuments = MutableLiveData<List<PolicyDocument>>()
    val policyDocuments: LiveData<List<PolicyDocument>>
        get() = _policyDocuments

    private val _petPicture = MutableLiveData<GlideUrlWithParameters>()
    val petPicture: LiveData<GlideUrlWithParameters>
        get() = _petPicture

    val documentBeingDownloaded = MutableLiveData<PolicyDocument?>()

    val documentToOpen = SingleLiveEvent<Uri>()

    val isLoadingPicture = MutableLiveData(false)

    val petBreedAndAge = coverage.map {
        it.pet?.getFormattedBreedAndAge(context)
    }

    val coverageNumber = coverage.map {
        context.resources.getString(R.string.policy_number, it.policyExternalId.toString()).uppercase()
    }

    fun setCoverage(coverage: PetPolicy) {
        _coverage.value = coverage
        getPolicyDocuments(coverage.id ?: return)
    }

    private fun getPolicyDocuments(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            policyRepository.getPolicyDocuments(id).onSuccess {
                val documents = this.body
                _policyDocuments.postValue(documents)
            }.onError {
                _networkError.postValue(ErrorWithRetry(this) { getPolicyDocuments(id) })
            }
        }
    }

    fun downloadPolicyDocument(document: PolicyDocument) {
        if (documentBeingDownloaded.value != null) return

        val id = coverage.value?.id ?: return
        val docId = document.id ?: return
        val filename = document.filename ?: return

        documentBeingDownloaded.value = document

        viewModelScope.launch(Dispatchers.IO) {
            documentToOpen.postValue(fileManager.getPolicyDocument(id, docId, filename))
            documentBeingDownloaded.postValue(null)
        }
    }

    fun updatePetPicture(pictureBase64: PictureBase64) {
        val petId = coverage.value?.pet?.id

        val petPictureBody = PetPictureBody(
            petId = petId,
            petPictureBase64 = pictureBase64
        )

        launchLoading(Dispatchers.IO, isLoadingPicture) {
            petsRepository.updatePetPicture(petPictureBody).onSuccess {
                if (petId != null) {
                    getUpdatedPet(petId)
                }
            }.onError {
                _networkError.postValue(
                    ErrorWithRetry(this) {
                        updatePetPicture(pictureBase64)
                    }
                )
            }
        }
    }

    private suspend fun getUpdatedPet(petId: Long) {
        petsRepository.getPetDetails(petId).onSuccess {
            body.getCacheableImage()?.let {
                _petPicture.postValue(it)
            }
        }
    }
}
