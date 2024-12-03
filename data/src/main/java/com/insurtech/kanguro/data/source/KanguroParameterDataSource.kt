package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.domain.model.KanguroParameter

interface KanguroParameterDataSource {

    suspend fun getAdvices(keyTopic: InformationTopics): List<KanguroParameter>
}
