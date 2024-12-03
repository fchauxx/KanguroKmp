package com.insurtech.kanguro.usecase

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.RentersPolicy

interface IGetPolicyUseCase {

    suspend fun fetchPetPoliciesResult(): Result<List<PetPolicy>>

    suspend fun fetchRentersPoliciesResult(): Result<List<RentersPolicy>>

    fun getPetPolicies(): List<PetPolicy>

    fun getRentersPolicies(): List<RentersPolicy>
}
