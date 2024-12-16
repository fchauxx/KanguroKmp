import UIKit

class PlanCoveredViewController: UIViewController {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var diagnosticBlotchyLabelsCardView: BlotchyLabelsCardView!
    @IBOutlet private var proceduresBlotchyLabelsCardView: BlotchyLabelsCardView!
    @IBOutlet private var medicationsBlotchyLabelsCardView: BlotchyLabelsCardView!
    
    // MARK: - Stored Properties
    let spacing: CGFloat = 24
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension PlanCoveredViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
}

// MARK: - Setup
extension PlanCoveredViewController {
    
    private func setupLayout() {
        setupLabels()
        setupDiagnosticBlotchyView()
        setupProceduresBlotchyView()
        setupMedicationsBlotchyView()
        setupBlotchyViewsImages()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "whatIsCovered.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupDiagnosticBlotchyView() {
        diagnosticBlotchyLabelsCardView.setupStackViews(stackViewSide: .left,
                                                        blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.bloodTest.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.urinalysis.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.krays.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.mri.label".localized)
                                                        ], spacing: spacing)
        diagnosticBlotchyLabelsCardView.setupStackViews(stackViewSide: .right,
                                                        blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.labwork.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.ctscans.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.ultrasounds.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.behavioral.label".localized)
                                                        ], spacing: spacing)
    }
    
    private func setupProceduresBlotchyView() {
        proceduresBlotchyLabelsCardView.setupStackViews(stackViewSide: .left,
                                                        blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.prosthetic.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.cremation.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.endLife.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.vetExam.label".localized)
                                                        ], spacing: spacing)
        proceduresBlotchyLabelsCardView.setupStackViews(stackViewSide: .right,
                                                        blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.surgery.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.specialty.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.cancer.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.dental.label".localized)
                                                        ], spacing: spacing)
    }
    
    private func setupMedicationsBlotchyView() {
        medicationsBlotchyLabelsCardView.setupStackViews(stackViewSide: .left,
                                                         blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.chronic.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.hereditary.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.prescription.label".localized)
                                                         ], spacing: spacing)
        medicationsBlotchyLabelsCardView.setupStackViews(stackViewSide: .right,
                                                         blotchyStringViewData: [
                                                            BlotchyLabelData(text: "whatIsCovered.blood.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.eye.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.musculokeletal.label".localized),
                                                            BlotchyLabelData(text: "whatIsCovered.respiratory.label".localized)
                                                         ], spacing: spacing*2)
    }
    
    private func setupBlotchyViewsImages() {
        guard let diagnosticImage = UIImage(named: "diagnostics"),
              let proceduresImage = UIImage(named: "procedures"),
              let medicationsImage = UIImage(named: "medications") else { return }
        diagnosticBlotchyLabelsCardView.setup(title: "whatIsCovered.diagnostic.label".localized,
                                              topImage: diagnosticImage)
        proceduresBlotchyLabelsCardView.setup(title: "whatIsCovered.procedures.label".localized,
                                              topImage: proceduresImage)
        medicationsBlotchyLabelsCardView.setup(title: "whatIsCovered.medications.label".localized,
                                               topImage: medicationsImage)
    }
}

// MARK: - IB Actions
extension PlanCoveredViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
