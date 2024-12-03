package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.mapper.KanguroParameterMapper
import com.insurtech.kanguro.data.source.KanguroParameterDataSource
import com.insurtech.kanguro.domain.model.KanguroParameter
import com.insurtech.kanguro.networking.api.KanguroVetAdvicesApiService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class KanguroParameterRemoteDataSource @Inject constructor(
    private val kanguroVetAdvicesApiService: KanguroVetAdvicesApiService
) : KanguroParameterDataSource {

    override suspend fun getAdvices(keyTopic: InformationTopics): List<KanguroParameter> =
        managedExecution {
            KanguroParameterMapper.mapKanguroParameterDtosToKanguroParameters(
                kanguroVetAdvicesApiService.getVetAdvices(keyTopic)
            )
        }
}
