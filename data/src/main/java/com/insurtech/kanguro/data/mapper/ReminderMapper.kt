package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.Reminder
import com.insurtech.kanguro.domain.model.ReminderType
import com.insurtech.kanguro.networking.dto.ReminderDto
import com.insurtech.kanguro.networking.dto.ReminderTypeDto

object ReminderMapper {

    fun mapReminderDtoToReminder(reminderDto: ReminderDto, pet: Pet): Reminder? {
        return Reminder(
            id = reminderDto.id ?: return null,
            type = when (reminderDto.type) {
                ReminderTypeDto.Claim -> ReminderType.Claim
                ReminderTypeDto.MedicalHistory -> ReminderType.MedicalHistory
                ReminderTypeDto.PetPicture -> ReminderType.PetPicture
                null -> return null
            },
            pet = pet,
            dueDate = reminderDto.dueDate,
            claimId = reminderDto.claimId
        )
    }
}
