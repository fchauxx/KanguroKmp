import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroRentersDomain

public struct ScheduleItemCategoryView: View, PresenterNavigationProtocol {
    
    // MARK: - Dependencies
    @ObservedObject var viewModel: ScheduleItemCategoryViewModel
    @Environment(\.appLanguageValue) var language
    
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    public typealias ScheduleItemCategoryClosure = ItemCategoryCardView<ScheduledItemCategory>.ScheduleItemCategoryClosure
    
    var didTapCategorySelected: ScheduleItemCategoryClosure
    var backAction: SimpleClosure?
    var closeAction: SimpleClosure?
    
    // MARK: - Initializer
    public init(viewModel: ScheduleItemCategoryViewModel,
                didTapCategorySelected: @escaping ScheduleItemCategoryClosure,
                backAction: SimpleClosure? = nil,
                closeAction: SimpleClosure? = nil
    ) {
        self.viewModel = viewModel
        self.didTapCategorySelected = didTapCategorySelected
        self.backAction = backAction
        self.closeAction = closeAction
    }
    
    public var body: some View {
        VStack {
            PresenterNavigationHeaderView(backAction: backAction)
                .padding(.horizontal, InsetSpacing.md)
                .padding(.top, InsetSpacing.xs)
            VStack {
                Text("scheduled.add.new.item.label".localized(lang))
                    .headlineSecondaryDarkestBold()
                    .padding(.vertical, StackSpacing.xxs)
                ScrollView(showsIndicators: false) {
                    ForEach(viewModel.itemCategoryList) { item in
                        ItemCategoryCardView(image: item.category.image,
                                             name: item.label,
                                             itemCategory: item.category) { _ in
                            didTapCategorySelected(item)
                        }
                    }
                }
            }
            .padding(.horizontal, StackSpacing.xs)
        }
        .navigationBarBackButtonHidden(true)
        .onAppear {
            viewModel.getScheduledItemsCategories()
        }
    }
}
