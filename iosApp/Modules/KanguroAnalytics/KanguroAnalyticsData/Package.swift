// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroAnalyticsData",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "KanguroAnalyticsData",
            targets: ["KanguroAnalyticsData"]),
    ],
    dependencies: [
        .package(name: "KanguroAnalyticsDomain", path: "../KanguroAnalyticsDomain"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMinor(from: "10.26.0"))
    ],
    targets: [
        .target(
            name: "KanguroAnalyticsData",
            dependencies: [
                .product(name: "KanguroAnalyticsDomain", package: "KanguroAnalyticsDomain"),
                .product(name: "FirebaseAnalytics", package: "firebase-ios-sdk")
            ]),
        .testTarget(
            name: "KanguroAnalyticsDataTests",
            dependencies: ["KanguroAnalyticsData"]),
    ]
)
