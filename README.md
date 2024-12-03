# Kanguro Project Documentation

## Project Brief

This project titled `Kanguro` is developed in Android Studio with Kotlin, implementing the Model-View-ViewModel (MVVM) architecture. It integrates `Compose` and `XML` for its UI design while the navigation is orchestrated using `XML`.

The project fundamentally revolves around two core features:
1. Facilitating pet insurance.
2. Allowing users to insure their properties.

# Build and Test
Our repository is divided into the following main branches, and have these rules:
Branch | Can push directly? | PR Requirements | Min PR approvals | PR Triggers
--- | :-: | --- | :-: | ---- |
`dev` | ✅ | Unit tests passing | 1 | Triggers internal test build (internal test) |
`stage` | ❌ | Unit tests passing | 1 | Triggers client test build (alpha) |
`main` | ❌ | Unit and instrumentation tests passing | 1 | Triggers production build |

#### To build dev release version:
To build the app in dev release, the user must set up some configurations:
1. Make sure that you have Git installed. On terminal, choose the path you want and type:
- `git clone https://github.com/kanguroseguro/kanguro-android`
2. Go to Android Studio and open the project already installed on the previous step, on the same path. `kanguro-android` must appear.
3. Create a file named `local.properties`.
    - If you are dev, go to 1Password and get the credentials and paste it to the file
    - If you are a client, request the keys to one of our Android devs and paste it to the file
4. In left side panel, open `Build Variables`.
5. Go to Active Build Variable column and set the :app module to `devRelease`.
6. Go to SDK setup and configuration https://console.firebase.google.com/project/kanguro-seguro-dev/settings/general/android:com.insurtech.kanguro.dev, download `google-services.json` and put it on the root of the :app module.
7. If Edit Configuration dialog shows up, locate Installation Option > Deploy, and switch from "Default APK" to "APK from app Bundle" to make it work. Otherwise, ignore this step.
8. That's it, you are ready to go. Select any emulator, request an user for dev environment with one of our devs and be happy!

#### To build dev debug version:
To build the app in dev debug it is more simple:
1. Make sure that you have Git installed. On terminal, choose the path you want and type:
   - `git clone https://github.com/kanguroseguro/kanguro-android`
2. Go to Android Studio and open the project already installed on the previous step, on the same path. `kanguro-android` must appear.
3. That's it, you are ready to go. Select any emulator, request an user for dev environment with one of our devs and be happy!


# Layout Guidelines 🤠
To make putting layouts together easier and to maintain cohesion on the app, we created some custom components and styles that should be used when possible, instead of creating new components from scratch (when possible). They are all based on the original design document from Figma.

The default Kanguro Components are located inside `designsystem\ui\composables\commumComponents`.

## Chronology -  Before and after Compose

In the beginning, the app was basically built for Pet Insurance (that's why they named it Kanguro). At that time, Compose wasn't in the mainstream, so we developed the Pet Insurance mostly with XML. After some time, we added another feature: Renters Insurance. Starting from that point, we introduced Jetpack Compose.

## MVVM

The project uses MVVM architecture to construct a neat structure and a manageable flow, making it so much easier to maintain on the long run.

## Tech Stack

- Android Studio
- Kotlin
- Compose and XML
- MVVM architecture
- Hilt

## Main features


### Pet Insurance

This feature allows users to insure their pets. Users can input details about their pets, including breed, age, health conditions, etc., to find suitable insurance policies. They can also compare different insurance plans, view detailed policy information, and purchase a plan of their choosing directly from the app.

### Renters Insuerance

Kanguro Renters Insurance is a solution designed for renters. It protects our client's personal belongings from unforeseen circumstances and also covers personal liability costs. It's a all-encompassing insurance solution that offers security for a rented living space.

## Navigation

Navigation in this application has always been implemented using XML, which is responsible for the transition between different screens and passing data accordingly. Every feature, be it pet insurance or renting a property, can be accessed with ease on a navigation graph called `nav_home.xml`.
