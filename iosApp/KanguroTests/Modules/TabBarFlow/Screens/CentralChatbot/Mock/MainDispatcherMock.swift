import Foundation
import Kanguro

public struct MainDispatcherMock: Dispatcher {
    public func async(target: DispatchType, group: DispatchGroup?, execute work: @escaping () -> Void) {
        work()
    }
    
    public func asyncAfter(target: DispatchType, deadline: DispatchTime, execute work: @escaping () -> Void) {
        work()
    }
}
