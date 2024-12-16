// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroUserDomain",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroUserDomain",
            targets: ["KanguroUserDomain"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../KanguroSharedDomain")
    ],
    targets: [
        .target(
            name: "KanguroUserDomain",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
            ]),
        .testTarget(
            name: "KanguroUserDomainTests",
            dependencies: ["KanguroUserDomain"]),
    ]
)
