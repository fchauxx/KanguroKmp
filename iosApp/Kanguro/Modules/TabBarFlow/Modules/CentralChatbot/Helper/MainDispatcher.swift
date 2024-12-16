import Foundation

public enum DispatchType {
    case main
    case global
}

public struct MainDispatcher: Dispatcher {
        
    public func async(target: DispatchType, group: DispatchGroup? = nil, execute work: @escaping () -> Void) {
        if target == .global {
            DispatchQueue.global(qos: .userInitiated).async(group: group) {
                work()
            }
        } else {
            DispatchQueue.main.async(group: group) {
                work()
            }
        }
    }

    public func asyncAfter(target: DispatchType, deadline: DispatchTime, execute work: @escaping () -> Void) {
        if target == .global {
            DispatchQueue.global(qos: .userInitiated).asyncAfter(deadline: deadline, execute: work)
        } else {
            DispatchQueue.main.asyncAfter(deadline: deadline, execute: work)
        }
    }
}
