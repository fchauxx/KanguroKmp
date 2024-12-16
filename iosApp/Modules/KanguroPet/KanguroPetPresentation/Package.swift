// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroPetPresentation",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroPetPresentation",
            targets: ["KanguroPetPresentation"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroPetDomain", path: "../KanguroPetDomain"),
        .package(name: "KanguroDesignSystem", path: "../../KanguroDesignSystem"),
        .package(name: "KanguroAnalyticsDomain", path: "../../KanguroAnalyticsDomain"),
        .package(url: "https://github.com/hmlongco/Resolver", branch: "master")
    ],
    targets: [
        .target(
            name: "KanguroPetPresentation",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroPetDomain", package: "KanguroPetDomain"),
                .product(name: "KanguroDesignSystem", package: "KanguroDesignSystem"),
                .product(name: "KanguroAnalyticsDomain", package: "KanguroAnalyticsDomain"),
                .product(name: "Resolver", package: "resolver")
            ]),
        .testTarget(
            name: "KanguroPetPresentationTests",
            dependencies: ["KanguroPetPresentation"]),
    ]
)
