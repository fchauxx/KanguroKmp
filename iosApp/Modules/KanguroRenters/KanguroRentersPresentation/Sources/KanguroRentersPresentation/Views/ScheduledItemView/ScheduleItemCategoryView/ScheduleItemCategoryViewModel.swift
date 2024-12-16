import SwiftUI
import KanguroRentersDomain

public class ScheduleItemCategoryViewModel: ObservableObject {
    
    // MARK: - Wrapped Properties
    @Published public var itemCategoryList: [ScheduledItemCategory]
    @Published var isLoading = true
    
    // MARK: - Network Protocols
    private var getScheduledItemsCategoriesService: GetScheduledItemsCategoriesUseCaseProtocol
    
    public init(itemCategoryList: [ScheduledItemCategory],
                getScheduledItemsCategoriesService: GetScheduledItemsCategoriesUseCaseProtocol) {
        self.itemCategoryList = itemCategoryList
        self.getScheduledItemsCategoriesService = getScheduledItemsCategoriesService
    }
}

// MARK: - Network
extension ScheduleItemCategoryViewModel {
    
    public func getScheduledItemsCategories() {
        getScheduledItemsCategoriesService.execute() { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let categories):
                DispatchQueue.main.async {
                    self.itemCategoryList = categories
                    self.isLoading = false
                }
            case .failure(let error):
                debugPrint(error)
            }
        }
    }
}
