package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.TemporaryFile
import com.insurtech.kanguro.networking.dto.TemporaryFileDto

object TemporaryFileMapper {

    fun mapTemporaryFileDtoToTemporaryFile(temporaryFileDto: TemporaryFileDto) =
        TemporaryFile(
            id = temporaryFileDto.id,
            url = temporaryFileDto.url
        )
}
