package com.insurtech.kanguro.core.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.KanguroPetsApiService
import com.insurtech.kanguro.domain.model.Pet
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object RepoUtils {

    suspend fun getPetDetails(petsApiService: KanguroPetsApiService, ids: Set<Long>): Map<Long, Pet> = coroutineScope {
        return@coroutineScope ids.map { petId ->
            async {
                (petsApiService.getPetDetail(petId) as? NetworkResponse.Success)?.body?.let {
                    petId to it
                }
            }
        }.awaitAll().filterNotNull().toMap()
    }
}
