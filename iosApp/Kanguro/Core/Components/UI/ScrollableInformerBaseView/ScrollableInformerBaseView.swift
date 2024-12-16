import UIKit
import KanguroSharedDomain

class ScrollableInformerBaseView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var topImageView: UIImageView!
    @IBOutlet private var informerAccordionList: InformerAccordionViewList!
    @IBOutlet private var titleLabelsView: HeaderLabelsView!
    @IBOutlet private var loaderView: UIActivityIndicatorView!
    
    @IBOutlet private var backButtonView: UIView!
    @IBOutlet private var scrollView: UIScrollView!
    @IBOutlet private var contentView: UIView!
    @IBOutlet private var headerImageHeight: NSLayoutConstraint!
    @IBOutlet private var minimumBottomScrollableConstraint: NSLayoutConstraint!
    @IBOutlet private var maximumTopScrollableConstraint: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var image: UIImage?
    var informerData: [InformerData]?
    var titleLabelsData: TitleLabelsViewData?
    private var isVetCard: Bool = true
    private var maxHeaderHeight: CGFloat = 300
    private var previousScrollOffset: CGFloat = 0
    private let minHeaderHeight: CGFloat = 72
    private let minSafeAreaDistance: CGFloat = -72
    
    // MARK: - Computed Properties
    var bottomScrollLimit: CGFloat {
        return (minSafeAreaDistance + contentView.cornerRadius)
    }
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
        setupLoaderView()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension ScrollableInformerBaseView {
    
    private func setupLayout() {
        setupInformerAccordionList()
        setupActions()
    }
    
    private func setupLoaderView() {
        loaderView.setScaleSize(1.2)
    }
    
    private func setupImage() {
        guard let image = image else { return }
        topImageView.image = image
    }
    
    private func setupConstraints() {
        minimumBottomScrollableConstraint.constant = minSafeAreaDistance
        maximumTopScrollableConstraint.constant = minHeaderHeight
    }
    
    private func setScrollPosition() {
        self.scrollView.contentOffset = CGPoint(x: 0, y: 0)
    }
    
    private func setupInformerAccordionList() {
        guard let data = getFormattedData() else { return }
        informerAccordionList.setup(with: data)
        informerAccordionList.isHidden = false
        loaderView.stopAnimating()
    }
    
    private func getFormattedData() -> [InformerData]? {
        guard let informerData else { return nil }
        
        let formattedData = informerData.map { item -> InformerData in
            var newItem = item
            newItem.value = newItem.value?
                .replaceFirstOccurrence(of: "^[0-9.\\s\\W]+", with: "")
                .getOnlyFirstCharacterCapitalized
            return newItem
        }
        
        return formattedData
    }
    
    private func setupTitleLabelsView() {
        guard let data = titleLabelsData else { return }
        titleLabelsView.setup(data, isVetCard: isVetCard)
    }
    
    private func setupActions() {
        for item in informerAccordionList.stackViewSubviews {
            item.didTapExpansionAction = { [weak self] in
                guard let self = self else { return }
                self.closeAccordionViews(openView: item)
            }
        }
    }
    
    private func closeAccordionViews(openView: InformerAccordionView) {
        var accordionViews = informerAccordionList.stackViewSubviews
        if let index = accordionViews.firstIndex(where: { $0 === openView }) {
            accordionViews.remove(at: index)
        }
        if openView.isExpanded {
            accordionViews.forEach {
                if $0.isExpanded { $0.close() }
            }
        }
    }
}

// MARK: - Public Methods
extension ScrollableInformerBaseView {
    
    func update(image: UIImage) {
        self.image = image
        setupImage()
    }
    
    func update(backAction: @escaping SimpleClosure) {
        self.backAction = backAction
    }
    
    func setupData(_ informerData: [InformerData]) {
        self.informerData = informerData
        setupLayout()
    }
    
    func setupTitleLabelsData(_ data: TitleLabelsViewData, isVetCard: Bool) {
        self.titleLabelsData = data
        self.isVetCard = isVetCard
        setupTitleLabelsView()
    }
}

// MARK: - UIScrollViewDelegate
extension ScrollableInformerBaseView: UIScrollViewDelegate {
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView.contentOffset.y < bottomScrollLimit { scrollView.contentOffset.y = bottomScrollLimit }
        let scrollDiff = (scrollView.contentOffset.y - previousScrollOffset)
        let isScrollingDown = scrollDiff > 0
        let isScrollingUp = scrollDiff < 0
        if canHeaderBeAnimated(scrollView) {
            var newHeight = headerImageHeight.constant
            if isScrollingDown {
                newHeight = max(minHeaderHeight, headerImageHeight.constant - abs(scrollDiff))
            } else if isScrollingUp {
                newHeight = min(maxHeaderHeight, headerImageHeight.constant + abs(scrollDiff))
            }
            if newHeight != headerImageHeight.constant {
                headerImageHeight.constant = newHeight
                setScrollPosition()
                previousScrollOffset = scrollView.contentOffset.y
            }
        }
    }
    
    func canHeaderBeAnimated(_ scrollView: UIScrollView) -> Bool {
        let scrollViewMaxHeight = scrollView.frame.height + self.headerImageHeight.constant - minHeaderHeight
        return scrollView.contentSize.height > scrollViewMaxHeight
    }
}

// MARK: - Public Methods
extension ScrollableInformerBaseView {
    
    @IBAction private func goBackButton(_ sender: UIButton) {
        backAction()
    }
}
