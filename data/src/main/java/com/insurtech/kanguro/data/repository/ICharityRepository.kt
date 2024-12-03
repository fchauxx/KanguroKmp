package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Charity
import com.insurtech.kanguro.domain.model.Donation

interface ICharityRepository {

    suspend fun getAll(): Result<List<Charity>>

    suspend fun syncUserDonation(donation: Donation): Result<Unit>
}
