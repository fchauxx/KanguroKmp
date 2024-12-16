import SwiftUI

public enum PathType {
    case normal
    case sheet
}

@available(iOS 16.0, *)
public class PetRouter<Page: Hashable>: ObservableObject {

    // MARK: - Wrapped Properties
    @Published public var path = NavigationPath()
    @Published public var sheetPath = NavigationPath()
    @Published public var sheet: Page?

    public init() {}

    // MARK: - Public Methods
    public func reset(pathType: PathType = .normal) {
        switch pathType {
        case .normal:
            path = NavigationPath()
        case .sheet:
            sheetPath = NavigationPath()
        }
    }

    public func push(_ page: any Hashable, pathType: PathType = .normal) {
        switch pathType {
        case .normal:
            path.append(page)
        case .sheet:
            sheetPath.append(page)
        }

    }

    public func pop(pathType: PathType = .normal) {
        switch pathType {
        case .normal:
            path.removeLast()
        case .sheet:
            sheetPath.removeLast()
        }

    }

    public func popToRoot(pathType: PathType = .normal) {
        switch pathType {
        case .normal:
            path.removeLast(path.count)
        case .sheet:
            sheetPath.removeLast(sheetPath.count)
        }
    }

    public func present(page: Page) {
        sheet = page
    }
}
