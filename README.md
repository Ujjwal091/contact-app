# Contact Manager Android App

A modern Android contact management application built with Jetpack Compose and Clean Architecture principles. This app allows users to manage their contacts with full CRUD (Create, Read, Update, Delete) operations while following Android development best practices.

## 📱 Features

- **Contact List**: View all contacts in a clean, organized list
- **Contact Details**: View detailed information about each contact
- **Add Contact**: Create new contacts with comprehensive information
- **Edit Contact**: Update existing contact information
- **Delete Contact**: Remove contacts with confirmation dialog
- **Permissions Handling**: Proper Android contacts permissions management
- **Modern UI**: Material 3 design with Jetpack Compose
- **Responsive Design**: Optimized for different screen sizes

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```
app/
├── data/                    # Data layer
│   ├── datasource/         # Data sources (local/remote)
│   ├── model/              # Data models and mappers
│   └── repository/         # Repository implementations
├── domain/                  # Domain layer
│   ├── entity/             # Business entities
│   ├── repository/         # Repository interfaces
│   └── usecase/            # Business use cases
└── presentation/           # Presentation layer
    ├── ui/                 # UI screens and components
    ├── navigation/         # Navigation setup
    └── component/          # Reusable UI components
```

### Architecture Layers

- **Data Layer**: Handles data operations, including local storage and external APIs
- **Domain Layer**: Contains business logic, entities, and use cases
- **Presentation Layer**: UI components, ViewModels, and user interaction handling

## 🛠️ Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture Pattern**: MVVM with Clean Architecture
- **Dependency Injection**: Koin
- **Navigation**: Navigation Compose
- **Design System**: Material 3
- **Permissions**: Accompanist Permissions
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

## 📦 Dependencies

### Core Dependencies
- **Jetpack Compose**: Modern UI toolkit
- **Navigation Compose**: Type-safe navigation
- **ViewModel Compose**: State management
- **Material 3**: Modern design components
- **Koin**: Lightweight dependency injection
- **Accompanist Permissions**: Permission handling

### Development Dependencies
- **JUnit**: Unit testing
- **Espresso**: UI testing
- **Compose Testing**: Compose-specific testing utilities

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with API level 35
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd android_native
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Sync the project**
   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync to complete

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Permissions

The app requires the following permissions:
- `READ_CONTACTS`: To read existing contacts
- `WRITE_CONTACTS`: To create, update, and delete contacts

These permissions are requested at runtime when the app starts.

## 📱 App Screens

### 1. Contact List Screen
- Displays all contacts in a scrollable list
- Each contact shows name, phone number, and avatar
- Floating action button to add new contacts
- Tap on contact to view details

### 2. Contact Detail Screen
- Shows comprehensive contact information
- Options to edit or delete the contact
- Clean, card-based layout for easy reading

### 3. Add Contact Screen
- Form to create new contacts
- Input fields for name, phone, email, address, company
- Validation and error handling
- Save and cancel options

### 4. Edit Contact Screen
- Pre-populated form with existing contact data
- Same fields as Add Contact screen
- Update and cancel options

## 🏛️ Project Structure

```
com.example.myapplication/
├── MyApplication.kt                 # Application class with Koin setup
├── data/
│   ├── datasource/
│   │   ├── ContactDataSource.kt     # Data source interface
│   │   └── ContactDataSourceImpl.kt # Data source implementation
│   ├── model/
│   │   ├── ContactModel.kt          # Data model
│   │   └── ContactMapper.kt         # Entity-Model mapper
│   └── repository/
│       └── ContactRepositoryImpl.kt # Repository implementation
├── domain/
│   ├── entity/
│   │   └── Contact.kt               # Business entity
│   ├── repository/
│   │   └── ContactRepository.kt     # Repository interface
│   └── usecase/
│       ├── AddContactUseCase.kt     # Add contact business logic
│       ├── DeleteContactUseCase.kt  # Delete contact business logic
│       ├── GetAllContactsUseCase.kt # Get contacts business logic
│       ├── GetContactByIdUseCase.kt # Get single contact business logic
│       └── UpdateContactUseCase.kt  # Update contact business logic
├── di/
│   └── AppModule.kt                 # Dependency injection module
└── presentation/
    ├── MainActivity.kt              # Main activity
    ├── navigation/
    │   └── Navigation.kt            # Navigation setup
    ├── component/
    │   └── ContactAvatar.kt         # Reusable components
    └── ui/
        ├── addcontact/              # Add contact feature
        ├── contactdetail/           # Contact detail feature
        ├── contactlist/             # Contact list feature
        ├── editcontact/             # Edit contact feature
        ├── permissions/             # Permission handling
        └── theme/                   # App theming
```

## 🧪 Testing

The project includes:
- **Unit Tests**: Business logic testing
- **Instrumented Tests**: Android-specific testing
- **Compose Tests**: UI component testing

Run tests using:
```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests
```

## 🎨 Design Patterns

- **MVVM**: Model-View-ViewModel for presentation layer
- **Repository Pattern**: Abstraction for data access
- **Use Case Pattern**: Encapsulation of business logic
- **Dependency Injection**: Loose coupling with Koin
- **Observer Pattern**: State management with Compose State

## 🔧 Configuration

### Build Configuration
- **Compile SDK**: 35
- **Min SDK**: 24
- **Target SDK**: 35
- **Java Version**: 11
- **Kotlin JVM Target**: 11

### ProGuard
ProGuard is configured but disabled in debug builds. Enable for release builds by setting `isMinifyEnabled = true` in `build.gradle.kts`.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Contact

For questions or support, please open an issue in the repository.

---

**Built with ❤️ using modern Android development practices**