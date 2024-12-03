package com.insurtech.kanguro.ui.scenes.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.databinding.FragmentSettingsBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsBottomSheetFragment : KanguroBottomSheetFragment<FragmentSettingsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Settings

    @Inject
    lateinit var preferences: SharedPreferences

    override val viewModel: SettingsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentSettingsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCloseButton()
        setSelectedLanguage()
        handleLanguageChange()
    }

    private fun setCloseButton() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setSelectedLanguage() {
        if (preferences.preferredLanguage == AppLanguage.English) {
            binding.englishOption.isChecked = true
            binding.spanishOption.isChecked = false
        } else {
            binding.spanishOption.isChecked = true
            binding.englishOption.isChecked = false
        }
    }

    private fun handleLanguageChange() {
        binding.languageSelector.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.englishOption -> viewModel.updateUserPreferredLanguage(AppLanguage.English, requireActivity())
                else -> viewModel.updateUserPreferredLanguage(AppLanguage.Spanish, requireActivity())
            }
        }
    }
}
