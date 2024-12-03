package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain

data class ScheduledItemsCategoryItemModelUi(
    val id: String,
    val label: String
)

fun getScheduledItemsCategoryItemModelUiMockList(): List<ScheduledItemsCategoryItemModelUi> {
    return listOf(
        ScheduledItemsCategoryItemModelUi(id = "Jewelry", label = "Jewelry"),
        ScheduledItemsCategoryItemModelUi(
            id = "FineArtsAndCollectibles",
            label = "Fine Art \n& Collectibles"
        ),
        ScheduledItemsCategoryItemModelUi(id = "Electronics", label = "Electronics"),
        ScheduledItemsCategoryItemModelUi(id = "MusicalInstruments", label = "Musical Instruments"),
        ScheduledItemsCategoryItemModelUi(
            id = "DesignerClothingAndAccessories",
            label = "Designer Clothing \n and Accessories"
        ),
        ScheduledItemsCategoryItemModelUi(id = "SportsEquipment", label = "Sporting Equipment"),
        ScheduledItemsCategoryItemModelUi(
            id = "HighValueAppliances",
            label = "High-Value Appliances"
        ),
        ScheduledItemsCategoryItemModelUi(
            id = "RareBooksAndManuscripts",
            label = "Rare Books And Manuscripts"
        ),
        ScheduledItemsCategoryItemModelUi(
            id = "HomeOfficeAndHomeVideoEquipment",
            label = "Home Office And Home Video Equipment"
        ),
        ScheduledItemsCategoryItemModelUi(id = "ElectricScooter", label = "Electric Scooter"),
        ScheduledItemsCategoryItemModelUi(id = "Others", label = "Others")
    )
}
