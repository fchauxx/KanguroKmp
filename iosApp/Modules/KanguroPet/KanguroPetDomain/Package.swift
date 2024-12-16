// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroPetDomain",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroPetDomain",
            targets: ["KanguroPetDomain"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain")
    ],
    targets: [
        .target(
            name: "KanguroPetDomain",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
            ]),
        .testTarget(
            name: "KanguroPetDomainTests",
            dependencies: ["KanguroPetDomain"]),
    ]
)
