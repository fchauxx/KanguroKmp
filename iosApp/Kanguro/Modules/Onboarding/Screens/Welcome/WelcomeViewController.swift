import UIKit
import SwiftUI

class WelcomeViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: WelcomeViewModel!
    
    // MARK: Stored Properties
    var timer: Timer?
    var delay: Double = 4
    
    // MARK: - Actions
    var goToSignInAction: SimpleClosure = {}
    var didTapGetAQuoteAction: LanguageClosure = { _ in }
    
    // MARK: - IB Outlets
    @IBOutlet var scrollingImages: [UIImageView]!
    @IBOutlet private var scrollView: UIScrollView!
    @IBOutlet var slide1DescriptionLabel: CustomLabel!
    @IBOutlet var slide2DescriptionLabel: CustomLabel!
    @IBOutlet private var slide3DescriptionLabel: CustomLabel!
    @IBOutlet private var slide3SubDescriptionLabel: CustomLabel!
    @IBOutlet private var pageControl: UIPageControl!
    @IBOutlet weak var switchLanguageLabel: CustomLabel!
    @IBOutlet var signInButton: CustomButton!
    @IBOutlet private var getQuoteLabel: CustomLabel!
    @IBOutlet private var getQuoteButton: CustomButton!
}

// MARK: - Life Cycle
extension WelcomeViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        setupTimer()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        timer?.invalidate()
    }
}

// MARK: - View State
extension WelcomeViewController {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
        default:
            break
        }
    }
}

// MARK: - Setup
extension WelcomeViewController {
    
    private func setupLayout() {
        setupButtons()
        setupLabels()
        setupImages()
        setupGetQuote()
    }
    
    private func setupImages() {
        scrollingImages.forEach { image in
            image.roundCorners(corners: [.bottomLeft, .bottomRight], radius: 24)
        }
    }
    
    func setupLabels() {
        
        let boldStyle = TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24)
        var regularStyle = boldStyle
        regularStyle.color = .secondaryDark
        let language = viewModel.alternativeLanguage == .Spanish ? "welcome.spanish.label".localized : viewModel.alternativeLanguage.title
        
        slide1DescriptionLabel.setHighlightedText(text: "welcome.description1.label".localized,
                                                  style: regularStyle,
                                                  highlightedText: "welcome.descriptionBold1.label".localized,
                                                  highlightedStyle: boldStyle)
        switchLanguageLabel.set(text: "welcome.switchLanguage.button".localized + language,
                                style: TextStyle(weight: .bold, underlined: true))
        
        slide2DescriptionLabel.setHighlightedText(text: "welcome.description2.label".localized,
                                                  style: boldStyle,
                                                  highlightedText: "welcome.descriptionBold2.label".localized)
        
        slide3DescriptionLabel.set(text: "welcome.description3.label".localized,
                                   style: boldStyle)
        slide3SubDescriptionLabel.set(text: "welcome.subdescription3.label".localized,
                                      style: TextStyle(color: .primaryDarkest, weight: .bold, size: .p21, font: .raleway))
    }
    
    func setupButtons() {
        signInButton.set(title: "welcome.signIn.button".localized, style: .primary)
        signInButton.onTap { [weak self] in
            guard let self = self else { return }
            self.goToSignInAction()
        }
    }
    
    private func setupGetQuote() {
        getQuoteLabel.set(text: "welcome.getQuote.label".localized, style: TextStyle(color: .neutralDark, size: .p12, alignment: .center))
        getQuoteButton.set(title: "welcome.getQuote.button".localized, style: .underlined(color: .secondaryDarkest))
        getQuoteButton.onTap { [weak self] in
            guard let self else { return }
            self.didTapGetAQuoteAction(viewModel.currentLanguage)
        }
    }
    
    @objc private func changeSliderPage() {
        if pageControl.currentPage >= 2 { return }
        let x = CGFloat(pageControl.currentPage + 1) * scrollView.frame.size.width
        scrollView.setContentOffset(CGPoint(x: x, y: 0), animated: true)
    }
    
    private func updateLayout() {
        setupLabels()
        setupButtons()
        setupGetQuote()
    }
}

// MARK: - Timer
extension WelcomeViewController {
    
    private func setupTimer() {
        timer = Timer.scheduledTimer(timeInterval: delay,
                                     target: self,
                                     selector: #selector(self.changeSliderPage),
                                     userInfo: nil,
                                     repeats: true)
    }
}

// MARK: - ScrollViewDelegate
extension WelcomeViewController: UIScrollViewDelegate {
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        self.view.layoutIfNeeded()
        if scrollView.contentOffset.y != 0 {
            scrollView.contentOffset.y = 0
        }
        updatePageControl(scrollView: scrollView)
    }
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let pageNumber = round(scrollView.contentOffset.x / scrollView.frame.size.width)
        pageControl.currentPage = Int(pageNumber)
        timer?.invalidate()
        setupTimer()
    }
    
    private func updatePageControl(scrollView: UIScrollView) {
        
        let scrollX = scrollView.contentOffset.x
        let percent = scrollX / scrollView.contentSize.width
        
        if percent < 0.33 {
            pageControl.currentPage = 0
        } else if percent < 0.66 {
            pageControl.currentPage = 1
        } else {
            pageControl.currentPage = 2
        }
    }
}

// MARK: - IB Actions
extension WelcomeViewController {
    
    @IBAction private func switchLanguageTouchUpInside(_ sender: UIButton) {
        viewModel.switchLanguage()
        updateLayout()
    }
}
