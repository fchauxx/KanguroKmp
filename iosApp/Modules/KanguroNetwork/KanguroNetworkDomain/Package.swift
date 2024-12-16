// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroNetworkDomain",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroNetworkDomain",
            targets: ["KanguroNetworkDomain"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../KanguroSharedDomain")
    ],
    targets: [
        .target(
            name: "KanguroNetworkDomain",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain")
            ]),
        .testTarget(
            name: "KanguroNetworkDomainTests",
            dependencies: ["KanguroNetworkDomain"]),
    ]
)
