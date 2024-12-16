// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroSharedPresentation",
    defaultLocalization: "en",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroSharedPresentation",
            targets: ["KanguroSharedPresentation"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../KanguroSharedDomain"),
        .package(name: "KanguroDesignSystem", path: "../KanguroDesignSystem")
    ],
    targets: [
        .target(
            name: "KanguroSharedPresentation",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroDesignSystem", package: "KanguroDesignSystem")
            ]),
        .testTarget(
            name: "KanguroSharedPresentationTests",
            dependencies: ["KanguroSharedPresentation"]),
    ]
)
