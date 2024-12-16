import Foundation
import SwiftUI
import KanguroRentersDomain
import KanguroChatbotDomain

public class ScheduleItemSummaryViewModel: ObservableObject {
    
    // MARK: - Wrapped Properties
    @Published var isLoading = true
    @Published var showError = false
    
    // MARK: - Stored Properties
    var policyId: String
    var scheduledItemsList: [ScheduledItem]
    var selectedScheduledItemToDeleteId: String?
    
    // MARK: - Delegates
    weak var delegate: ChatbotExternalFlowDelegate?
    
    // MARK: - Network Protocols
    private var getScheduledItemsService: GetScheduledItemsUseCaseProtocol
    private var deleteScheduledItemService: DeleteScheduledItemUseCaseProtocol
    
    public init(isLoading: Bool = true,
                policyId: String,
                scheduledItemsList: [ScheduledItem] = [],
                delegate: ChatbotExternalFlowDelegate? = nil,
                getScheduledItemsService: GetScheduledItemsUseCaseProtocol,
                deleteScheduledItemService: DeleteScheduledItemUseCaseProtocol) {
        self.isLoading = isLoading
        self.policyId = policyId
        self.scheduledItemsList = scheduledItemsList
        self.delegate = delegate
        self.getScheduledItemsService = getScheduledItemsService
        self.deleteScheduledItemService = deleteScheduledItemService
    }
    
    func addPictures() {
        
    }
    
    func submitItems() {
        guard let delegate else { return }
        delegate.didReceiveExternalFlowResponse("", chatText: nil)
    }
    
    // MARK: - Network
    public func getScheduledItems() {
        self.isLoading = true
        getScheduledItemsService.execute(policyId: policyId) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let scheduledItems):
                self.scheduledItemsList = scheduledItems
            case .failure(let error):
                debugPrint(error)
                self.showError = true
            }
            withAnimation(.easeOut(duration: 1)) {
                self.isLoading = false
            }
        }
    }
    
    public func deleteScheduledItem(id: String) {
        self.isLoading = true
        deleteScheduledItemService.execute(policyId: policyId,
                                           scheduledItemId: id) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success():
                self.getScheduledItems()
            case .failure(let error):
                debugPrint(error)
                self.showError = true
            }
            self.isLoading = false
        }
    }
}
