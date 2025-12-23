# Revidd Video App

This is a sample Android application demonstrating a **modularized, scalable architecture** using:

- **Clean Architecture**
- **Jetpack Compose UI**
- **MVI Pattern**
- **Multi-module setup** (`data`, `domain`, `presentation`)
- **Koin for Dependency Injection**
- **Kotlin Coroutines + Flow*

### Note:

> **The original problem statement was simple and didn’t require feature modularization.**  
> However, the project is deliberately designed this way **only for architectural demonstration and scalability purposes**.

## 🧱 Project Structure

Revidd/
├── app/ # Main application module (hosts the MainActivity)
├── data/ # Implements repository interfaces, handles API + local db
├── domain/ # Contains business models and use case interfaces
└── presentation/
└── feature-consumption/ # Feature module for consumption. All Video consumption should land here but for now its all in app due to time constraints (Compose UI, ViewModel, DI)

# Unit Tests
Unit tests not written yet

# Android Studio Version Used
Android Studio Narwhal | 2025.1.1 Patch 1


## Common Issues
If you see **"Invalid JDK configuration"**:
- Open Android Studio → `File > Project Structure > SDK Location` and set jdk
- This is the latest stable version as mentioned in requirements sheet. 
- Please run on this version to avoid any Gradle issues
