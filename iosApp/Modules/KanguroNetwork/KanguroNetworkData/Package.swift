// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroNetworkData",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "KanguroNetworkData",
            targets: ["KanguroNetworkData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
        .package(name: "KanguroNetworkDomain", path: "../KanguroNetworkDomain"),
        .package(name: "KanguroUserDomain", path: "../../KanguroUser/KanguroUserDomain"),
        .package(url: "https://github.com/Alamofire/Alamofire", .upToNextMinor(from: "5.9.1")),
        .package(url: "https://github.com/getsentry/sentry-cocoa", .upToNextMinor(from: "8.22.4"))
    ],
    targets: [
        .target(
            name: "KanguroNetworkData",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain"),
                .product(name: "KanguroNetworkDomain", package: "KanguroNetworkDomain"),
                .product(name: "KanguroUserDomain", package: "KanguroUserDomain"),
                .product(name: "Alamofire", package: "Alamofire"),
                .product(name: "Sentry", package: "sentry-cocoa")
            ]),
        .testTarget(
            name: "KanguroNetworkDataTests",
            dependencies: ["KanguroNetworkData"]),
    ]
)
