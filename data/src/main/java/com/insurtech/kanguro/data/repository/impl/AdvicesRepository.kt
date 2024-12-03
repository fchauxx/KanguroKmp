package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IAdvicesRepository
import com.insurtech.kanguro.data.source.KanguroParameterDataSource
import com.insurtech.kanguro.domain.model.KanguroParameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdvicesRepository @Inject constructor(
    private val kanguroParameterDataSource: KanguroParameterDataSource
) : IAdvicesRepository {

    override suspend fun getAdvices(keyTopic: InformationTopics): Flow<Result<List<KanguroParameter>>> =
        flow {
            try {
                val advices = kanguroParameterDataSource.getAdvices(keyTopic)
                emit(Result.Success(advices))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun getAdvicesResult(keyTopic: InformationTopics): Result<List<KanguroParameter>> {
        return try {
            val advices = kanguroParameterDataSource.getAdvices(keyTopic)
            Result.Success(advices)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
