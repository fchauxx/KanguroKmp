package com.insurtech.kanguro.domain.model

import java.math.BigDecimal

data class ScheduledItemModel(
    val id: String,
    val itemName: String,
    val type: String,
    val itemPrice: BigDecimal,
    var isValid: Boolean
)

fun getScheduledItemModelMock() = listOf(
    ScheduledItemModel("id_0", "Macbook", "MusicInstrument", BigDecimal(4000), true),
    ScheduledItemModel("id_1", "Painting 1", "MusicInstrument", BigDecimal(1000), true),
    ScheduledItemModel("id_2", "Painting 2", "MusicInstrument", BigDecimal(1500), false),
    ScheduledItemModel("id_3", "Bike 1", "MusicInstrument", BigDecimal(2000), false)
)

fun scheduledItemViewModelToScheduledItemModel(viewModel: ScheduledItemViewModel): ScheduledItemModel {
    val isValid = viewModel.images?.isNotEmpty() ?: false

    return ScheduledItemModel(
        id = viewModel.id ?: "",
        itemName = viewModel.name,
        type = viewModel.type ?: "",
        itemPrice = BigDecimal(viewModel.valuation ?: 0.0),
        isValid = isValid
    )
}
