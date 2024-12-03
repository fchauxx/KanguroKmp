package com.insurtech.kanguro.ui.scenes.vetAdvice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.common.enums.KanguroParameterType
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IAdvicesRepository
import com.insurtech.kanguro.domain.model.KanguroParameter
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class InformationsTopicsViewModel @Inject constructor(
    private val advicesRepository: IAdvicesRepository
) : BaseViewModel() {

    private val _advicesLoad = MutableLiveData<List<KanguroParameter>>()
    val advicesLoad: LiveData<List<KanguroParameter>>
        get() = _advicesLoad

    fun getAdvices(keyTopic: InformationTopics, type: KanguroParameterType? = null) {
        launchLoading(Dispatchers.IO) {
            advicesRepository.getAdvices(keyTopic)
                .catch { e -> Result.Error(Exception(e)) }
                .collect { kanguroParametersResult ->
                    if (kanguroParametersResult is Result.Success) {
                        handleSuccess(type, kanguroParametersResult)
                    } else {
                        val errorResult = kanguroParametersResult as Result.Error
                        handleError(errorResult, keyTopic, type)
                    }
                }
        }
    }

    private fun handleSuccess(
        type: KanguroParameterType?,
        kanguroParametersResult: Result.Success<List<KanguroParameter>>
    ) {
        when (type) {
            KanguroParameterType.VA_D,
            KanguroParameterType.VA_C -> {
                val filteredParameters =
                    kanguroParametersResult.data.filter { it.type == type }
                _advicesLoad.postValue(filteredParameters)
            }

            else -> {
                val parameters = kanguroParametersResult.data
                _advicesLoad.postValue(parameters)
            }
        }
    }

    private fun handleError(
        errorResult: Result.Error,
        keyTopic: InformationTopics,
        type: KanguroParameterType?
    ) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    errorResult.exception,
                    null
                )
            ) {
                getAdvices(keyTopic, type)
            }
        )
    }
}
