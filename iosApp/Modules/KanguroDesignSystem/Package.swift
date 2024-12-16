// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroDesignSystem",
    defaultLocalization: "en",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroDesignSystem",
            targets: ["KanguroDesignSystem"]),
    ],
    dependencies: [
        .package(url: "https://github.com/airbnb/lottie-spm.git", .upToNextMinor(from: "4.4.1")),
        .package(url: "https://github.com/onevcat/Kingfisher.git", .upToNextMinor(from: "7.11.0")),
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain")
    ],
    targets: [
        .target(
            name: "KanguroDesignSystem",
            dependencies: [
                .product(name: "Lottie", package: "lottie-spm"),
                .product(name: "Kingfisher", package: "Kingfisher"),
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain")
            ],
            resources: [.process("Resources/Fonts")]
        ),
        .testTarget(
            name: "KanguroDesignSystemTests",
            dependencies: ["KanguroDesignSystem"]),
    ]
)
