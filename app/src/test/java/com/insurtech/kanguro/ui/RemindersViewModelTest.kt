package com.insurtech.kanguro.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.insurtech.kanguro.core.getOrAwaitValue
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.ui.scenes.reminders.RemindersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RemindersViewModelTest {

    private lateinit var viewModel: RemindersViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = RemindersViewModel()
    }

    @Test
    fun `Should show all reminders`() {
        // Arrange
        val allReminders = buildFakeReminders()

        // Act
        viewModel.clearRemindersFilter()
        viewModel.setReminders(allReminders)

        // Assert
        Assert.assertEquals(
            allReminders.count(),
            viewModel.filteredReminders.getOrAwaitValue().count()
        )
    }

    @Test
    fun `Should filter reminder by petId`() {
        // Arrange
        val allReminders = buildFakeReminders()

        // Act
        viewModel.updateSelectedFilter(1L)
        viewModel.setReminders(allReminders)

        // Assert
        Assert.assertEquals(
            1,
            viewModel.filteredReminders.getOrAwaitValue().count()
        )

        Assert.assertEquals(
            1L,
            viewModel.filteredReminders.getOrAwaitValue().first().pet.id
        )
    }

    private fun buildFakeReminders(): List<Reminder> {
        return arrayListOf(
            Reminder(id = "id1", type = ReminderType.Claim, pet = Pet(id = 1L), null, null),
            Reminder(id = "id2", type = ReminderType.Claim, pet = Pet(id = 2L), null, null),
            Reminder(id = "id3", type = ReminderType.Claim, pet = Pet(id = 3L), null, null),
            Reminder(id = "id4", type = ReminderType.Claim, pet = Pet(id = 4L), null, null)
        )
    }
}
