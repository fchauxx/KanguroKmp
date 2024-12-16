// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroFeatureFlagDomain",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroFeatureFlagDomain",
            targets: ["KanguroFeatureFlagDomain"]),
    ],
    dependencies: [
    ],
    targets: [
        .target(
            name: "KanguroFeatureFlagDomain",
            dependencies: []),
        .testTarget(
            name: "KanguroFeatureFlagDomainTests",
            dependencies: ["KanguroFeatureFlagDomain"]),
    ]
)
