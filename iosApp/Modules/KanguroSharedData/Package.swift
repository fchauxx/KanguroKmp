// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroSharedData",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroSharedData",
            targets: ["KanguroSharedData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../KanguroSharedDomain"),
        .package(name: "KanguroNetworkDomain", path: "../KanguroNetwork/KanguroNetworkDomain")
    ],
    targets: [
        .target(
            name: "KanguroSharedData",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroNetworkDomain", package: "KanguroNetworkDomain")
            ]),
        .testTarget(
            name: "KanguroSharedDataTests",
            dependencies: ["KanguroSharedData"]),
    ]
)
