// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroStorageData",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "KanguroStorageData",
            targets: ["KanguroStorageData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroStorageDomain", path: "../KanguroStorageDomain")
    ],
    targets: [
        .target(
            name: "KanguroStorageData",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroStorageDomain", package: "KanguroStorageDomain"),
            ]
        ),
        .testTarget(
            name: "KanguroStorageDataTests",
            dependencies: ["KanguroStorageData"],
            path: "Tests"
        )
    ]
)
