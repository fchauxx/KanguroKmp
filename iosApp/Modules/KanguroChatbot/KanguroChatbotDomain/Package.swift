// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroChatbotDomain",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroChatbotDomain",
            targets: ["KanguroChatbotDomain"])
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
    ],
    targets: [
        .target(
            name: "KanguroChatbotDomain",
            dependencies: ["KanguroSharedDomain"]),
        .testTarget(
            name: "KanguroChatbotDomainTests",
            dependencies: ["KanguroChatbotDomain"]),
    ]
)
