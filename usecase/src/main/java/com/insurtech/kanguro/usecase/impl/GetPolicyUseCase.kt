package com.insurtech.kanguro.usecase.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.usecase.IGetPolicyUseCase
import javax.inject.Inject

class GetPolicyUseCase @Inject constructor(
    private val petPolicyRepository: IRefactoredPolicyRepository,
    private val rentersPolicyRepository: IRentersPolicyRepository
) : IGetPolicyUseCase {

    private var petPolicies: Result<List<PetPolicy>>? = null
    private var rentersPolicies: Result<List<RentersPolicy>>? = null

    override suspend fun fetchPetPoliciesResult(): Result<List<PetPolicy>> {
        petPolicies = petPolicyRepository.getPolicies()
        return petPolicies!!
    }

    override suspend fun fetchRentersPoliciesResult(): Result<List<RentersPolicy>> {
        rentersPolicies = rentersPolicyRepository.getPolicies()
        return rentersPolicies!!
    }

    override fun getPetPolicies(): List<PetPolicy> {
        return petPolicies?.data.orEmpty()
    }

    override fun getRentersPolicies(): List<RentersPolicy> {
        return rentersPolicies?.data.orEmpty()
    }
}
