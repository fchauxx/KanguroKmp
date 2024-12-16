//
//  HomeFAQView.swift
//
//
//  Created by Mateus Vagner on 26/04/24.
//

import Foundation
import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroRentersDomain

public struct HomeFAQView: View {
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: HomeFAQViewModel
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Computed Properties
    var backAction: (SimpleClosure)
    var lang: String {
        language.rawValue
    }
    
    public init(viewModel: HomeFAQViewModel, backAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.backAction = backAction
    }
    
    public var body: some View {
        ZStack {
            if !viewModel.requestError.isEmpty {
                createErrorDataView()
            } else if !viewModel.isLoading {
                ScrollingViewScreenBase(
                    contentView: AnyView(createContentView()),
                    headerImage: Image.homeFaqImage,
                    backAction: backAction
                )
            } else {
                LoadingView()
            }
        }
        .onAppear {
            viewModel.getInformerData()
        }
    }

}

// MARK: - Content Views
public extension HomeFAQView {
    
    @ViewBuilder
    func createContentView() -> some View {
        VStack(alignment: .leading, spacing: StackSpacing.xs) {
            VStack(alignment: .leading, spacing: 0) {
                HighlightedText(text: "faq.title.label".localized(lang),
                                highlightedText: "faq.title.label".localized(lang),
                                baseStyle: TextStyle(
                                    font: .museo(.museoSansBold, size: 21),
                                    color: .secondaryDarkest
                                ),
                                highlightedStyle: TextStyle(
                                    font: .museo(.museoSansBold, size: 21),
                                    color: .primaryDarkest
                                )
                )
                
                Text("faq.subtitle.label".localized(lang))
                    .bodySecondaryDarkestBold()
            }
            
            if !viewModel.vetInformerDatas.isEmpty {
                createFAQSection(title: "faq.subtitle.label.pet".localized(lang), informerDatas: viewModel.vetInformerDatas)
            }
            
            if !viewModel.rentersInformerDatas.isEmpty {
                createFAQSection(title: "faq.subtitle.label.renters".localized(lang), informerDatas: viewModel.rentersInformerDatas)
            }
        }
        .padding(.horizontal, InsetSpacing.xs)
    }
    
    @ViewBuilder
    private func createCardContent(label: String?) -> some View {
        Text(label ?? "error")
            .foregroundColor(.gray)
            .padding([.horizontal, .bottom], InsetSpacing.xxs)
    }
    
    @ViewBuilder
    private func createErrorDataView() -> some View {
        DataStatusResponseView(image: Image.requestFailedImage,
                               title: "scheduled.error1.state".localized(lang),
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "scheduled.error2.state".localized(lang),
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
        .onTapGesture {
            viewModel.getInformerData()
        }
    }
    
    @ViewBuilder
    private func createFAQSection(title: String, informerDatas: [InformerData]) -> some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(title)
                .bodyPrimaryDarkestBold()
            ForEach(informerDatas.indices, id: \.self) { index in
                AccordionButtonView(
                    title: informerDatas[index].value?.removeStartingNumbersAndPunctuation ?? "error",
                    insideView: AnyView(createCardContent(label: informerDatas[index].description))
                )
                
            }.padding(.top, InsetSpacing.nano)
        }
    }
}

