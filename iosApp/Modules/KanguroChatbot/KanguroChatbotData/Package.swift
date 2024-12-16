// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroChatbotData",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroChatbotData",
            targets: ["KanguroChatbotData"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedData", path: "../../KanguroSharedData"),
        .package(name: "KanguroChatbotDomain", path: "../KanguroChatbotDomain"),
        .package(name: "KanguroNetworkDomain", path: "../../KanguroNetwork/KanguroNetworkDomain")
    ],
    targets: [
        .target(
            name: "KanguroChatbotData",
            dependencies: [
                .product(name: "KanguroSharedData", package: "KanguroSharedData"),
                .product(name: "KanguroChatbotDomain", package: "KanguroChatbotDomain"),
                .product(name: "KanguroNetworkDomain", package:  "KanguroNetworkDomain")
            ]),
        .testTarget(
            name: "KanguroChatbotDataTests",
            dependencies: [
                "KanguroChatbotData",
                .product(name: "KanguroChatbotDomain", package: "KanguroChatbotDomain")
            ]),
    ]
)
