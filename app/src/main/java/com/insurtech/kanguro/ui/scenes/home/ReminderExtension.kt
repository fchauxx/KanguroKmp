package com.insurtech.kanguro.ui.scenes.home

import com.insurtech.kanguro.common.enums.PetType
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ReminderTypeUiModel
import com.insurtech.kanguro.domain.model.Reminder
import com.insurtech.kanguro.domain.model.ReminderType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType as PetTypeUiModel

fun Reminder.toItemReminderUiModel(): ItemReminderUiModel =
    ItemReminderUiModel(
        id = id,
        type = when (type) {
            ReminderType.Claim -> ReminderTypeUiModel.Claim
            ReminderType.MedicalHistory -> ReminderTypeUiModel.MedicalHistory
            ReminderType.PetPicture -> ReminderTypeUiModel.PetPicture
        },
        petId = pet.id,
        petName = pet.name ?: "",
        petType = when (pet.type) {
            PetType.Dog -> PetTypeUiModel.Dog
            PetType.Cat -> PetTypeUiModel.Cat
            else -> PetTypeUiModel.Dog
        },
        petPictureUrl = pet.petPictureUrl ?: "",
        clinicName = "",
        dueDate = dueDate,
        medicationDate = null, // TODO
        claimId = claimId
    )
