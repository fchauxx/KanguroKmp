package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ScheduledItemViewModel
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ScheduledItemMapperTest {

    @Test
    fun `Map scheduled item DTO to scheduled item`() {
        // ARRANGE
        val scheduledItemsDto = "200_get_get_scheduled_items_successfully.json"
            .mapJsonToListOfModels<ScheduledItemViewModelDto>()

        // ACT
        if (scheduledItemsDto != null) {
            val scheduledItems = scheduledItemsDto.map { scheduledItemDto ->
                ScheduledItemMapper.mapScheduledItemViewModelDtoToScheduledItemViewModel(scheduledItemDto)
            }

            // ASSERT
            assertEquals(
                expected = listOf(
                    ScheduledItemViewModel(
                        id = "1234",
                        name = "Diamond Ring",
                        type = "Jewelry",
                        valuation = 100.0,
                        images = null
                    ),
                    ScheduledItemViewModel(
                        id = "1234",
                        name = "Macbook Pro",
                        type = "Electronics",
                        valuation = 200.0,
                        images = null
                    ),
                    ScheduledItemViewModel(
                        id = "1234",
                        name = "Brown Scooter",
                        type = "ElectricScooter",
                        valuation = 300.0,
                        images = null
                    )
                ),
                actual = scheduledItems
            )
        } else {
            fail("ScheduledItemsDto must be not null.")
        }
    }
}
