package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.source.KanguroParameterDataSource
import com.insurtech.kanguro.domain.model.KanguroParameter
import java.lang.Exception

class FakeKanguroParameterDataSource : KanguroParameterDataSource {

    private var kanguroParameters: List<KanguroParameter>? = null
    private var exception: Exception? = null

    fun setKanguroParameters(kanguroParameters: List<KanguroParameter>) {
        this.kanguroParameters = kanguroParameters
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getAdvices(keyTopic: InformationTopics): List<KanguroParameter> =
        kanguroParameters ?: throw exception!!
}
