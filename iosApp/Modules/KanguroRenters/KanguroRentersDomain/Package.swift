// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "KanguroRentersDomain",
    platforms: [
            .iOS(.v16)
        ],
    products: [
        .library(
            name: "KanguroRentersDomain",
            targets: ["KanguroRentersDomain"]),
    ],
    dependencies: [
        .package(name: "KanguroSharedDomain", path: "../../KanguroSharedDomain"),
    ],
    targets: [
        .target(
            name: "KanguroRentersDomain",
            dependencies: [
                .product(name: "KanguroSharedDomain", package: "KanguroSharedDomain")
            ]),
        .testTarget(
            name: "KanguroRentersDomainTests",
            dependencies: ["KanguroRentersDomain"]),
    ]
)
