// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroRentersData",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroRentersData",
            targets: ["KanguroRentersData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedData", path: "../../KanguroSharedData"),
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroRentersDomain", path: "../KanguroRentersDomain"),
        .package(name: "KanguroNetworkDomain", path: "../../KanguroNetwork/KanguroNetworkDomain")
    ],
    targets: [
        .target(
            name: "KanguroRentersData",
            dependencies: [
                .product(name: "KanguroSharedData", package: "KanguroSharedData"),
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroRentersDomain", package: "KanguroRentersDomain"),
                .product(name: "KanguroNetworkDomain", package: "KanguroNetworkDomain")
            ]),
        .testTarget(
            name: "KanguroRentersDataTests",
            dependencies: ["KanguroRentersData"]),
    ]
)
