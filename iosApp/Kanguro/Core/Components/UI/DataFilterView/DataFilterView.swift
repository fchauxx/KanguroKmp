import UIKit

class DataFilterView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var textField: CustomTextField!
    @IBOutlet private var dataTableView: UITableView!
    
    // MARK: - Stored Properties
    private var data: [FilterData] = []
    private var placeholder = ""
    var text: String?
    
    // MARK: - Computed Properties
    var filteredData: [FilterData] {
        if let text = text?.lowercased(),
           !text.isEmpty {
            return data.filter { $0.title.lowercased().contains(text) || $0.isCustomOption }
        } else {
            return data
        }
    }
    
    // MARK: - Actions
    var didFinishAction: StringClosure = { _ in }
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension DataFilterView {
    
    private func setup() {
        setupTableView()
        setupTextField()
    }
    
    private func setupTableView() {
        dataTableView.register(identifiers: [DataFilterTableViewCell.identifier])
        dataTableView.reloadData()
    }
    
    private func setupTextField() {
        textField.set(placeholder: placeholder, color: .secondaryDarkest)
        textField.setup(type: .default, actions: TextFieldActions(didChangeAction: update(text:)))
    }
    
    private func handleCustomBankOption(text: String) {
        let hasCustomOption:Bool = data.first?.isCustomOption ?? false 
        hasCustomOption ? updateCustomBankOption(text: text) : self.data.insert(FilterData(id: -1, title: text), at: 0)
    }
    
    private func updateCustomBankOption(text: String) {
        if text.isEmpty {
            data.removeFirst()
        } else {
            data[0].title = text
        }
    }
    
    
}

// MARK: - Public Methods
extension DataFilterView {
    
    func setup(data: [FilterData], hasExternalTextField: Bool = false) {
        self.data = data
        textField.isHidden = hasExternalTextField
        setup()
    }
    
    func update(text: String) {
        self.text = text
        handleCustomBankOption(text: text)
        dataTableView.reloadData()
    }
    
    func update(placeholder: String) {
        self.placeholder = placeholder
        setupTextField()
    }
    
    func update(didFinishAction: @escaping StringClosure) {
        self.didFinishAction = didFinishAction
    }
}

// MARK: - DataSource & Delegate
extension DataFilterView: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filteredData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell: DataFilterTableViewCell = tableView.dequeueReusableCell(for: indexPath) else { return UITableViewCell() }
        let filteredDataItem = filteredData[indexPath.row]
        cell.setup(title: filteredDataItem.title, isCustomOption: filteredDataItem.isCustomOption)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        didFinishAction(filteredData[indexPath.row].title)
    }
}
