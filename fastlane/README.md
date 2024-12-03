fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android build_debug

```sh
[bundle exec] fastlane android build_debug
```

Build Project

### android unit_test

```sh
[bundle exec] fastlane android unit_test
```

Run Unit Tests

### android lint

```sh
[bundle exec] fastlane android lint
```

Run Lint Checks

### android ktlint

```sh
[bundle exec] fastlane android ktlint
```

Run ktlintCheck

### android to_dev

```sh
[bundle exec] fastlane android to_dev
```

Deploy Dev Flavor to Internal track on Play Store

### android to_stage

```sh
[bundle exec] fastlane android to_stage
```

Deploy Stage Flavor to Alpha track on Play Store

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
