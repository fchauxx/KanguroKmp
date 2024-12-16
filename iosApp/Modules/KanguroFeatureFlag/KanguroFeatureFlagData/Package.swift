// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroFeatureFlagData",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "KanguroFeatureFlagData",
            targets: ["KanguroFeatureFlagData"]),
    ],
    dependencies: [
        .package(name: "KanguroFeatureFlagDomain", path: "../KanguroFeatureFlagDomain"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMinor(from: "10.26.0"))
    ],
    targets: [
        .target(
            name: "KanguroFeatureFlagData",
            dependencies: [
                .product(name: "KanguroFeatureFlagDomain", package: "KanguroFeatureFlagDomain"),
                .product(name: "FirebaseRemoteConfig", package: "firebase-ios-sdk")
            ]),
        .testTarget(
            name: "KanguroFeatureFlagDataTests",
            dependencies: ["KanguroFeatureFlagData"]),
    ]
)
