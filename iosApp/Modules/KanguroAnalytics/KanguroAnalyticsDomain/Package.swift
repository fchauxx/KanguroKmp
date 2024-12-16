// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroAnalyticsDomain",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroAnalyticsDomain",
            targets: ["KanguroAnalyticsDomain"]),
    ],
    dependencies: [

    ],
    targets: [
        .target(
            name: "KanguroAnalyticsDomain",
            dependencies: []),
        .testTarget(
            name: "KanguroAnalyticsDomainTests",
            dependencies: ["KanguroAnalyticsDomain"]),
    ]
)
