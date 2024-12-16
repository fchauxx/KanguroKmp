// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroStorageDomain",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroStorageDomain",
            targets: ["KanguroStorageDomain"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain")
    ],
    targets: [
        .target(
            name: "KanguroStorageDomain",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain")
            ]),
        .testTarget(
            name: "KanguroStorageDomainTests",
            dependencies: ["KanguroStorageDomain"]),
    ]
)
