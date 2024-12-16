import KanguroRentersDomain
import SwiftUI

public extension ScheduledItemCategoryType {
    
    var image: Image {
        switch self {
        case .Jewelry:
            return Image.categoryJewelry
        case .Electronics:
            return Image.categoryElectronics
        case .MusicalInstruments:
            return Image.categoryMusical
        case .FineArtsAndCollectibles:
            return Image.categoryFineArt
        case .DesignerClothingAndAccessories:
            return Image.categoryDesign
        case .SportsEquipment:
            return Image.categorySport
        case .HighValueAppliances:
            return Image.categoryHighValue
        case .RareBooksAndManuscripts:
            return Image.categoryRareBooks
        case .HomeOfficeAndHomeVideoEquipment:
            return Image.categoryHomeVideo
        case .ElectricScooter:
            return Image.categoryElectricScooter
        }
    }
}
