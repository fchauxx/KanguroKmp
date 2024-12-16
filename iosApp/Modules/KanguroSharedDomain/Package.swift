// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroSharedDomain",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroSharedDomain",
            targets: ["KanguroSharedDomain"]),
    ],
    dependencies: [ // Domain should not have dependencies
    ],
    targets: [
        .target(
            name: "KanguroSharedDomain",
            dependencies: []),
        .testTarget(
            name: "KanguroSharedDomainTests",
            dependencies: ["KanguroSharedDomain"]),
    ]
)
