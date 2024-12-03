package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ScheduledItemImageType
import com.insurtech.kanguro.domain.model.ScheduledItemInputImage
import com.insurtech.kanguro.networking.dto.ScheduledItemImageTypeDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputImageDto

object ScheduledItemInputImageMapper {

    private fun mapScheduledItemInputImageToScheduledItemInputImageDto(scheduledItemInputImage: ScheduledItemInputImage) =
        ScheduledItemInputImageDto(
            fileId = scheduledItemInputImage.fileId,
            type = when (scheduledItemInputImage.type) {
                ScheduledItemImageType.ReceiptOrAppraisal -> ScheduledItemImageTypeDto.ReceiptOrAppraisal
                ScheduledItemImageType.Item -> ScheduledItemImageTypeDto.Item
                ScheduledItemImageType.ItemWithReceiptOrAppraisal -> ScheduledItemImageTypeDto.ItemWithReceiptOrAppraisal
                else -> null
            }
        )

    fun mapScheduledItemInputImageToScheduledItemInputImageDto(scheduledItemInputImage: List<ScheduledItemInputImage>) =
        scheduledItemInputImage.map { mapScheduledItemInputImageToScheduledItemInputImageDto(it) }
}
