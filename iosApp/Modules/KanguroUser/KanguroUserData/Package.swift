// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroUserData",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "KanguroUserData",
            targets: ["KanguroUserData"]),
    ],
    dependencies: [
        .package(name: "KanguroNetworkData", path: "../../KanguroNetwork/KanguroNetworkData"),
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroUserDomain", path: "../KanguroUserDomain"),
        .package(name: "KanguroStorageDomain", path: "../../KanguroStorage/KanguroStorageDomain"),
        .package(name: "KanguroNetworkDomain", path: "../../KanguroNetwork/KanguroNetworkDomain")
    ],
    targets: [
        .target(
            name: "KanguroUserData",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroUserDomain", package: "KanguroUserDomain"),
                .product(name: "KanguroStorageDomain", package: "KanguroStorageDomain"),
                .product(name: "KanguroNetworkDomain", package: "KanguroNetworkDomain"),
                .product(name: "KanguroNetworkData", package: "KanguroNetworkData")
            ]),
        .testTarget(
            name: "KanguroUserDataTests",
            dependencies: ["KanguroUserData"]),
    ]
)
