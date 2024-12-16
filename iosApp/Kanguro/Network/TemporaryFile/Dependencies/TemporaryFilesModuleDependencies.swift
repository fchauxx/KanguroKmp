import Foundation
import KanguroSharedDomain
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

class TemporaryFilesModuleDependencies {

    // MARK: - Stored Properties
    var temporaryFilesRepository: TemporaryFileRepositoryProtocol?
    var createTemporaryFileService: CreateTemporaryFilesUseCaseProtocol?

    // MARK: - Initializers
    init(temporaryFilesRepository: TemporaryFileRepositoryProtocol? = nil, 
         createTemporaryFileService: CreateTemporaryFilesUseCaseProtocol? = nil) {
        self.temporaryFilesRepository = temporaryFilesRepository
        self.createTemporaryFileService = createTemporaryFileService
    }
}

// MARK: - ModuleDependencies
extension TemporaryFilesModuleDependencies: ModuleDependencies {

    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let temporaryFileRepository = self.temporaryFilesRepository ?? TemporaryFileRepository(network: network)
        Resolver.register { temporaryFileRepository }

        let createTemporaryFileService = self.createTemporaryFileService ?? CreateTemporaryFile(temporaryFileRepo: temporaryFileRepository)
        Resolver.register { createTemporaryFileService }
    }
}
