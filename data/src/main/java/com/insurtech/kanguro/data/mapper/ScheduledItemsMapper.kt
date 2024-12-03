package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.dto.ScheduledItemTypeModelDto

object ScheduledItemsMapper {

    fun mapScheduledItemDtoListToScheduledItemModelList(dto: List<ScheduledItemTypeModelDto>?): List<ScheduledItemTypeModel>? {
        dto ?: return null

        return dto.map {
            val id = it.id
            val label = it.label

            if (id != null && label != null) {
                ScheduledItemTypeModel(
                    id = id,
                    label = label
                )
            } else {
                null
            }
        }.mapNotNull { it }
    }
}
