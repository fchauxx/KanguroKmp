package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain

import androidx.annotation.DrawableRes
import com.insurtech.kanguro.designsystem.R

enum class ScheduledItemsCategoryType {

    Jewelry,
    FineArtsAndCollectibles,
    Electronics,
    MusicalInstruments,
    DesignerClothingAndAccessories,
    SportsEquipment,
    HighValueAppliances,
    RareBooksAndManuscripts,
    HomeOfficeAndHomeVideoEquipment,
    ElectricScooter,
    Others
}

@DrawableRes
fun ScheduledItemsCategoryType.getIcon(): Int {
    return when (this) {
        ScheduledItemsCategoryType.Jewelry -> R.drawable.ic_jewelry
        ScheduledItemsCategoryType.FineArtsAndCollectibles -> R.drawable.ic_fine_art_collectibles
        ScheduledItemsCategoryType.Electronics -> R.drawable.ic_eletronics
        ScheduledItemsCategoryType.MusicalInstruments -> R.drawable.ic_musical_instruments
        ScheduledItemsCategoryType.DesignerClothingAndAccessories -> R.drawable.designer_clothing_accessories
        ScheduledItemsCategoryType.SportsEquipment -> R.drawable.ic_sporting_equipment
        ScheduledItemsCategoryType.HighValueAppliances -> R.drawable.ic_high_value_appliances
        ScheduledItemsCategoryType.RareBooksAndManuscripts -> R.drawable.ic_rare_books_and_manuscripts
        ScheduledItemsCategoryType.HomeOfficeAndHomeVideoEquipment -> R.drawable.ic_home_office_and_home_video_equipment
        ScheduledItemsCategoryType.ElectricScooter -> R.drawable.ic_electric_scooter
        else -> R.drawable.ic_scheduled_item_type_others
    }
}
