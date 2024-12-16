fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

### upload_debug_symbols

```sh
[bundle exec] fastlane upload_debug_symbols
```

Upload Debug Symbols

----


## iOS

### ios setup

```sh
[bundle exec] fastlane ios setup
```

Prepare your development environment for running and contributing

### ios app_store_connect_authentication

```sh
[bundle exec] fastlane ios app_store_connect_authentication
```

App Store Connect Authentication

### ios device

```sh
[bundle exec] fastlane ios device
```

Add new device

### ios rematch

```sh
[bundle exec] fastlane ios rematch
```

Renew provisioning profiles

### ios tinder

```sh
[bundle exec] fastlane ios tinder
```

Install all provisioning profiles and certificates

### ios build_project

```sh
[bundle exec] fastlane ios build_project
```

Generate .ipa file for environment

### ios get_derived_data_path

```sh
[bundle exec] fastlane ios get_derived_data_path
```

Build For Testing

### ios testing

```sh
[bundle exec] fastlane ios testing
```



### ios refresh_dsyms

```sh
[bundle exec] fastlane ios refresh_dsyms
```

Refresh dsyms for crashlytics error report on Firebase

### ios upload_alpha_dsyms

```sh
[bundle exec] fastlane ios upload_alpha_dsyms
```

Download and Upload of the dsyms to Firebase/Crashlytics ALPHA

### ios upload_beta_dsyms

```sh
[bundle exec] fastlane ios upload_beta_dsyms
```

Download and Upload of the dsyms to Firebase/Crashlytics BETA

### ios upload_prod_dsyms

```sh
[bundle exec] fastlane ios upload_prod_dsyms
```

Download and Upload of the dsyms to Firebase/Crashlytics PROD

### ios to_dev

```sh
[bundle exec] fastlane ios to_dev
```

Generate IPA and Symbols for Dev

### ios alpha_to_store

```sh
[bundle exec] fastlane ios alpha_to_store
```

Deliver Alpha to Test Flight

### ios to_beta

```sh
[bundle exec] fastlane ios to_beta
```

Generate IPA and Symbols for Stage

### ios beta_to_store

```sh
[bundle exec] fastlane ios beta_to_store
```

Deliver Beta to Test Flight

### ios increment_alpha_version

```sh
[bundle exec] fastlane ios increment_alpha_version
```

Increment alpha version

### ios increment_beta_version

```sh
[bundle exec] fastlane ios increment_beta_version
```

Increment beta version

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
