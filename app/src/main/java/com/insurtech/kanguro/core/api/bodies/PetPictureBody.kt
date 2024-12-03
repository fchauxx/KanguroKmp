package com.insurtech.kanguro.core.api.bodies

import com.insurtech.kanguro.domain.pet.PictureBase64

data class PetPictureBody(
    val petId: Long?,
    val petPictureBase64: PictureBase64
)
