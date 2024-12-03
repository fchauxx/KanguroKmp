package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.networking.dto.KanguroParameterDto
import retrofit2.http.GET
import retrofit2.http.Path

interface KanguroVetAdvicesApiService {

    @GET("api/KanguroParameter/GetInformationTopicByKeyAndLanguage/{key}")
    suspend fun getVetAdvices(@Path("key") keyTopic: InformationTopics): List<KanguroParameterDto>
}
