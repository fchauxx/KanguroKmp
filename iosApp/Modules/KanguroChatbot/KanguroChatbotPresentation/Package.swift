// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroChatbotPresentation",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroChatbotPresentation",
            targets: ["KanguroChatbotPresentation"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroDesignSystem", path: "../../KanguroDesignSystem"),
        .package(name: "KanguroChatbotDomain", path: "../KanguroChatbotDomain")
    ],
    targets: [
        .target(
            name: "KanguroChatbotPresentation",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroDesignSystem", package: "KanguroDesignSystem"),
                .product(name: "KanguroChatbotDomain", package: "KanguroChatbotDomain")
            ]),
        .testTarget(
            name: "KanguroChatbotPresentationTests",
            dependencies: ["KanguroChatbotPresentation"]),
    ]
)
