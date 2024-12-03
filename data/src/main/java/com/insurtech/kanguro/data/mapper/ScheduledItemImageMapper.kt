package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ScheduledItemImage
import com.insurtech.kanguro.domain.model.ScheduledItemImageType
import com.insurtech.kanguro.networking.dto.ScheduledItemImageDto
import com.insurtech.kanguro.networking.dto.ScheduledItemImageTypeDto

object ScheduledItemImageMapper {

    fun mapScheduleItemImageDtoToScheduleItemImage(scheduleItemImageDto: ScheduledItemImageDto): ScheduledItemImage =
        ScheduledItemImage(
            id = scheduleItemImageDto.id,
            fileName = scheduleItemImageDto.fileName,
            type = when (scheduleItemImageDto.type) {
                ScheduledItemImageTypeDto.ReceiptOrAppraisal -> ScheduledItemImageType.ReceiptOrAppraisal
                ScheduledItemImageTypeDto.Item -> ScheduledItemImageType.Item
                ScheduledItemImageTypeDto.ItemWithReceiptOrAppraisal -> ScheduledItemImageType.ItemWithReceiptOrAppraisal
                null -> null
            }
        )
}
