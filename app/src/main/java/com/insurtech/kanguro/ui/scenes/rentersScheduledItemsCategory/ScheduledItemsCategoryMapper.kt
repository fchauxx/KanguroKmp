package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsCategory

import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel

fun List<ScheduledItemTypeModel>.scheduledItemTypeModelListToUi(): List<ScheduledItemsCategoryItemModelUi> {
    return this.map {
        it.toUi()
    }
}

fun ScheduledItemTypeModel.toUi() = ScheduledItemsCategoryItemModelUi(
    id = this.id,
    label = this.label
)

fun ScheduledItemsCategoryItemModelUi.toDomain() = ScheduledItemTypeModel(
    id = this.id,
    label = this.label
)
