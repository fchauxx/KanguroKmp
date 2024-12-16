import UIKit
import SwiftUI
import Combine
import KanguroDesignSystem

open class BaseView: UIView {
    
    // MARK: - Properties
    private var cancellables: [Cancellable] = []
    
    // MARK: - Life Cycle
    deinit {
        removeObservers()
    }
    
    // MARK: - Observation
    public func observe<Type>(_ publisher: Published<Type>.Publisher, action: @escaping (Type) -> Void) {
        let cancellable = publisher
            .receive(on: DispatchQueue.main)
            .sink { value in
                action(value)
            }
        cancellables.append(cancellable)
    }
    
    public func removeObservers() {
        for cancellable in cancellables {
            cancellable.cancel()
        }
        cancellables.removeAll()
    }
}

// MARK: - Alert
extension BaseView {
    
    func showSimpleAlert(title: String = "alert.defaultTitle".localized, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "alert.defaultAction".localized, style: .cancel, handler: nil)
        alert.addAction(action)
        parentViewController?.present(alert, animated: true, completion: nil)
    }

    func showCustomSimpleAlert(popUpData: KanguroDesignSystem.PopUpData) {
        let image = UIHostingController(rootView: PopUpInfoView(
            data: popUpData,
            closeAction: {
                self.parentViewController?.dismiss(animated: true)
            }
        ))
        image.modalPresentationStyle = .overFullScreen
        image.view.backgroundColor = .clear
        parentViewController?.present(image, animated: true, completion: nil)
    }

    func showActionAlert(title: String = "alert.defaultTitle".localized,
                         message: String,
                         action: @escaping SimpleClosure) {
        
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "alert.defaultAction".localized,
                                        style: .default) { _ in
            action()
        }
        
        alert.addAction(alertAction)
        parentViewController?.present(alert, animated: true, completion: nil)
    }
    
    func showConfirmationAlert(title: String = "alert.attention".localized,
                               message: String,
                               confirmAction: @escaping SimpleClosure) {
        
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let cancelAction = UIAlertAction(title: "alert.cancel".localized,
                                         style: .default) { _ in }
        let confirmAction = UIAlertAction(title: "alert.confirm".localized,
                                          style: .default) { _ in
            confirmAction()
        }
        
        alert.addAction(cancelAction)
        alert.addAction(confirmAction)
        parentViewController?.present(alert, animated: true, completion: nil)
    }
}
