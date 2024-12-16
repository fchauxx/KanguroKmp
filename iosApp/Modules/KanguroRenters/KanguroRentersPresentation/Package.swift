// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroRentersPresentation",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroRentersPresentation",
            targets: ["KanguroRentersPresentation"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroRentersDomain", path: "../KanguroRentersDomain"),
        .package(name: "KanguroDesignSystem", path: "../../KanguroDesignSystem"),
        .package(name: "KanguroChatbotDomain", path: "../../KanguroChatbot/KanguroChatbotDomain"),
        .package(name: "KanguroUserDomain", path: "../../KanguroUser/KanguroUserDomain"),
        .package(url: "https://github.com/hmlongco/Resolver", branch: "master")
    ],
    targets: [
        .target(
            name: "KanguroRentersPresentation",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroRentersDomain", package: "KanguroRentersDomain"),
                .product(name: "KanguroDesignSystem", package: "KanguroDesignSystem"),
                .product(name: "KanguroChatbotDomain", package: "KanguroChatbotDomain"),
                .product(name: "KanguroUserDomain", package: "KanguroUserDomain"),
                .product(name: "Resolver", package: "resolver")

            ]),
        .testTarget(
            name: "KanguroRentersPresentationTests",
            dependencies: ["KanguroRentersPresentation"]),
    ]
)
