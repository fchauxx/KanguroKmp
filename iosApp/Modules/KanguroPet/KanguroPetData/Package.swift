// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroPetData",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroPetData",
            targets: ["KanguroPetData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroSharedData", path: "../../KanguroSharedData"),
        .package(name: "KanguroPetDomain", path: "../KanguroPetDomain"),
        .package(name: "KanguroNetworkDomain", path: "../../KanguroNetwork/KanguroNetworkDomain")
    ],
    targets: [
        .target(
            name: "KanguroPetData",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroSharedData", package: "KanguroSharedData"),
                .product(name: "KanguroPetDomain", package: "KanguroPetDomain"),
                .product(name: "KanguroNetworkDomain", package: "KanguroNetworkDomain")
            ]),
        .testTarget(
            name: "KanguroPetDataTests",
            dependencies: ["KanguroPetData"]),
    ]
)
