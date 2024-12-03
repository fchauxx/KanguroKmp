package com.insurtech.kanguro.ui.scenes.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.domain.dashboard.Reminder

class RemindersViewModel : BaseViewModel() {

    private val _allRemindersList = MutableLiveData<List<Reminder>>()

    private val selectedFilter = MutableLiveData(FILTER_ALL)

    private val _filteredReminders = MediatorLiveData<List<Reminder>>().apply {
        addSource(selectedFilter, ::filterRemindersList)
    }

    val filteredReminders: LiveData<List<Reminder>> = _filteredReminders

    fun setReminders(reminders: List<Reminder>) {
        _allRemindersList.value = reminders
        filterRemindersList(selectedFilter.value ?: FILTER_ALL)
    }

    fun clearRemindersFilter() {
        selectedFilter.value = FILTER_ALL
    }

    fun updateSelectedFilter(petId: Long) {
        selectedFilter.value = petId
    }

    private fun filterRemindersList(currentFilter: Long) {
        if (currentFilter == FILTER_ALL) {
            _filteredReminders.postValue(_allRemindersList.value)
        } else {
            _filteredReminders.postValue(_allRemindersList.value?.filter { it.pet.id == currentFilter })
        }
    }

    companion object {
        const val FILTER_ALL = -1L
    }
}
