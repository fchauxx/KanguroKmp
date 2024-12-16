import Foundation

class AppDelegateMock: NSObject {

    private let filename = "log_tests.txt"

    private var filepath: URL {
        guard let path = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first else { fatalError() }
        return path.appendingPathComponent(filename)
    }

    private var logMessageData: Data {
        let timestamp = Date().timeIntervalSince1970
        let textMessage = "Test started at \(timestamp)"
        guard let data = "\(textMessage)\n".data(using: .utf8, allowLossyConversion: false) else { fatalError() }
        return data
    }

    override init() {
        super.init()

        writeTestLog()
        // TODO: Refactor all tests not to depend on it
        AppDependencies.setup()
    }

    private func writeTestLog() {
        if FileManager.default.fileExists(atPath: filepath.path) {
            appendLog()
        } else {
            writeFirstLog()
        }
    }

    private func appendLog() {
        if let fileHandle = FileHandle(forWritingAtPath: filepath.path) {
            fileHandle.seekToEndOfFile()
            fileHandle.write(logMessageData)
            fileHandle.closeFile()
        }
    }

    private func writeFirstLog() {
        do {
            try logMessageData.write(to: filepath, options: .atomicWrite)
        } catch { }
    }
}
