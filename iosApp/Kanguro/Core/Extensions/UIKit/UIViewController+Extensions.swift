import UIKit
import SwiftUI
import KanguroDesignSystem

// MARK: - Keyboard
extension UIViewController {
    
    func hideKeyboardWhenTappedAround() {
        let tap = UITapGestureRecognizer(target: self, action: #selector(dismissKeyboard))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
    }
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
}

// MARK: - Alerts
extension UIViewController {
    
    func showSimpleAlert(title: String = "alert.defaultTitle".localized, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "alert.defaultAction".localized, style: .cancel, handler: nil)
        alert.addAction(action)
        present(alert, animated: true, completion: nil)
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
        present(alert, animated: true, completion: nil)
    }
    
    func showConfirmationAlert(title: String = "alert.attention".localized,
                               message: String,
                               confirmAction: @escaping SimpleClosure,
                               cancelAction: @escaping SimpleClosure) {
        
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let cancelAction = UIAlertAction(title: "alert.cancel".localized,
                                         style: .default) { _ in
            cancelAction()
        }
        let confirmAction = UIAlertAction(title: "alert.confirm".localized,
                                          style: .default) { _ in
            confirmAction()
        }
        
        alert.addAction(cancelAction)
        alert.addAction(confirmAction)
        present(alert, animated: true, completion: nil)
    }

    func showCustomSimpleAlert(popUpData: KanguroDesignSystem.PopUpData) {
        let image = UIHostingController(rootView: PopUpInfoView(data: popUpData,
                                                                closeAction: {
            self.dismiss(animated: true)
        }))
        image.modalPresentationStyle = .overFullScreen
        image.view.backgroundColor = .clear
        present(image, animated: true, completion: nil)
    }
}

// MARK: - Notification Center
extension UIViewController {
    
    func postNotification(name: Notification.Name, data: Any?) {
        NotificationCenter.default.post(name: name, object: data)
    }
}

public protocol Instantiable {
    
    static func instantiate() -> Self?
    static func instantiate(bundle: Bundle) -> Self?
}

extension UIViewController: Instantiable {
    
    public static func instantiate() -> Self? {
        return instantiate(bundle: Bundle.main)
    }
    
    public static func instantiate(bundle: Bundle) -> Self? {
        let filename = String(describing: Self.self)
        if bundle.path(forResource: filename, ofType: "storyboardc") != nil {
            let storyboard = UIStoryboard(name: filename, bundle: bundle)
            if let viewController = storyboard.instantiateInitialViewController() as? Self {
                return viewController
            }
            if let viewController = storyboard.instantiateViewController(identifier: filename) as? Self {
                return viewController
            }
        }
        if bundle.path(forResource: filename, ofType: "nib") != nil {
            return Self(nibName: filename, bundle: bundle)
        }
        return Self()
    }
}
