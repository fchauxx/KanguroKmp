package com.insurtech.kanguro.ui.scenes.chatbot

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.fromBase64
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IVetPlacesRepository
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChatBotMapViewModel @Inject constructor(
    private val vetPlacesRepository: IVetPlacesRepository
) : BaseViewModel() {

    private val _vetPlacesDetailed = MutableLiveData<List<VetPlace>>()
    val vetPlacesDetailed: LiveData<List<VetPlace>>
        get() = _vetPlacesDetailed

    private val _vetPlacesResumed = MutableLiveData<List<VetPlace>>()
    val vetPlacesResumed: LiveData<List<VetPlace>>
        get() = _vetPlacesResumed

    fun loadPlaces(userCurrentLocation: Location) {
        launchLoading(Dispatchers.IO) {
            vetPlacesRepository.getNearbyVetPlaces(
                userCurrentLocation,
                key = BuildConfig.PLACES_SDK_API_KEY.fromBase64()
            )
                .catch { e -> Result.Error(Exception(e)) }
                .collect { nearbySearchResult ->
                    when (nearbySearchResult) {
                        is Result.Success -> handleSuccessResult(
                            nearbySearchResult,
                            userCurrentLocation
                        )

                        is Result.Error -> handleErrorResult(
                            nearbySearchResult,
                            userCurrentLocation
                        )
                    }
                }
        }
    }

    private suspend fun handleSuccessResult(
        result: Result.Success<NearbyVetPlaceSearch>,
        userCurrentLocation: Location
    ) {
        val nearbyPlaces = arrayListOf<VetPlace>()

        nearbyPlaces.addAll(result.data.vetPlaces)
        nearbyPlaces.addAll(getAdditionalPlaces(userCurrentLocation, result.data.nextPageToken))

        _vetPlacesResumed.postValue(nearbyPlaces)

        if (nearbyPlaces.isNotEmpty()) {
            getPlacesByIds(nearbyPlaces, userCurrentLocation)
        }
    }

    private fun handleErrorResult(
        nearbySearchResult: Result.Error,
        userCurrentLocation: Location
    ) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    nearbySearchResult.exception,
                    null
                )
            ) {
                loadPlaces(userCurrentLocation)
            }
        )
    }

    private suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextPageToken: String?
    ): List<VetPlace> {
        val nearbyPlaces = arrayListOf<VetPlace>()
        var token = nextPageToken

        while (token != null) {
            delay(1500)
            token = getNextToken(userLocation, token, nearbyPlaces)
        }
        return nearbyPlaces
    }

    private suspend fun getNextToken(
        userLocation: Location,
        token: String?,
        nearbyPlaces: ArrayList<VetPlace>
    ): String? {
        var localToken = token

        if (localToken != null) {
            vetPlacesRepository.getAdditionalPlaces(
                userLocation,
                localToken,
                key = BuildConfig.PLACES_SDK_API_KEY.fromBase64()
            )
                .catch { e -> Result.Error(Exception(e)) }
                .collect { nearbySearchResult ->
                    localToken = when (nearbySearchResult) {
                        is Result.Success -> {
                            nearbyPlaces.addAll(nearbySearchResult.data.vetPlaces)
                            nearbySearchResult.data.nextPageToken
                        }

                        is Result.Error -> null
                    }
                }
        }

        return localToken
    }

    private suspend fun getPlacesByIds(places: List<VetPlace>, userCurrentLocation: Location) {
        val ids = places.mapNotNull { it.id }
        val placesWithDetail = arrayListOf<VetPlace>()

        for (id in ids) {
            vetPlacesRepository.getPlace(
                userCurrentLocation,
                id,
                key = BuildConfig.PLACES_SDK_API_KEY.fromBase64()
            )
                .catch { e -> Result.Error(Exception(e)) }
                .collect { placeResult ->
                    if (placeResult is Result.Success) {
                        placesWithDetail.add(placeResult.data)
                    }
                }
        }

        _vetPlacesDetailed.postValue(placesWithDetail.sortedBy { it.distanceFromUser })
    }
}
