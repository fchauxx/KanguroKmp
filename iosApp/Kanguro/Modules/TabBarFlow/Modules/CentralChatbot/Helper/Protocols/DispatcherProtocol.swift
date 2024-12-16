import Foundation

public protocol Dispatcher {
    
    func async(target: DispatchType, group: DispatchGroup?, execute work: @escaping () -> Void)
    func asyncAfter(target: DispatchType, deadline: DispatchTime, execute work: @escaping () -> Void)
}
