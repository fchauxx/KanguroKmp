package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.KanguroParameter
import kotlinx.coroutines.flow.Flow

interface IAdvicesRepository {

    suspend fun getAdvices(keyTopic: InformationTopics): Flow<Result<List<KanguroParameter>>>

    suspend fun getAdvicesResult(keyTopic: InformationTopics): Result<List<KanguroParameter>>
}
