package com.insurtech.kanguro.ui.scenes.settings

import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.utils.LanguageUtils
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val preferences: SharedPreferences
) : BaseViewModel() {

    fun updateUserPreferredLanguage(language: AppLanguage, activity: FragmentActivity) {
        val currentLanguage = preferences.preferredLanguage
        viewModelScope.launch {
            userRepository.setPreferredLanguage(language)
                .onSuccess {
                    if (currentLanguage != language) {
                        LanguageUtils.switchLanguage(preferences, activity, language)
                    }
                }
                .onError {
                    _networkError.postValue(
                        ErrorWithRetry(this) {
                            updateUserPreferredLanguage(language, activity)
                        }
                    )
                }
        }
    }
}
