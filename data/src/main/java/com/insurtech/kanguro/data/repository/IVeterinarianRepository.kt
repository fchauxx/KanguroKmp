package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Veterinarian

interface IVeterinarianRepository {

    suspend fun getVeterinarians(): Result<List<Veterinarian>>
}
