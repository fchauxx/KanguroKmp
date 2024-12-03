package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.TemporaryUploadModel
import com.insurtech.kanguro.networking.dto.TemporaryUploadModelDto

object TemporaryUploadModelMapper {

    fun mapTemporaryUploadModelDtoToTemporaryUploadModel(dto: TemporaryUploadModelDto): TemporaryUploadModel {
        return TemporaryUploadModel(
            id = dto.id,
            url = dto.url,
            blobType = dto.blobType
        )
    }
}
