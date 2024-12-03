package com.insurtech.kanguro.ui.scenes.petFaq

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IAdvicesRepository
import com.insurtech.kanguro.designsystem.removeStartingNumbersAndPunctuation
import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model.PetFaqModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetFrequentlyAskedQuestionsViewModel @Inject constructor(
    private val advicesRepository: IAdvicesRepository
) : BaseViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<PetFaqModel>>(UiState.Loading.ScreenLoader)
    val uiState = _uiState.asStateFlow()

    init {
        fetchFaq()
    }

    fun fetchFaq() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading.ScreenLoader
            var petFaq: List<QuestionModel> = emptyList()

            when (
                val rentersResult =
                    advicesRepository.getAdvicesResult(InformationTopics.FAQ)
            ) {
                is Result.Success -> {
                    petFaq = rentersResult.data.map {
                        QuestionModel(
                            question = it.value?.removeStartingNumbersAndPunctuation().orEmpty(),
                            answer = it.description.orEmpty()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.value = UiState.Error()
                    return@launch
                }
            }
            val model = PetFaqModel(
                petFaq = petFaq
            )

            _uiState.value = UiState.Success(model)
        }
    }
}
