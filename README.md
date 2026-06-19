# CadetTracker - NCC Learning, Training & Attendance Management Platform

CadetTracker is a comprehensive Android application built with MVVM architecture, Kotlin, and Firebase integration. It's designed to manage NCC (National Cadet Corps) cadet development, training, attendance, and learning.

## Features

- **Authentication**: Login, Registration, OTP Verification with Firebase Auth
- **Offline-First**: Full functionality without internet connectivity
- **Learning Management System**: Course management, learning content delivery, quizzes
- **Attendance Tracking**: Manual marking and QR code scanning
- **Event Management**: Event creation, registration, and tracking
- **Camp Management**: Camp organization and cadet registration
- **AI Tutor**: Chat-based assistance for cadets
- **Reports**: Generate and export reports
- **Data Synchronization**: Automatic sync when internet is available
- **Role-Based Access**: Different dashboards for different user roles

## Architecture

The project follows MVVM (Model-View-ViewModel) architecture with:

- **Data Layer**: Repositories, Local DB (Room), Remote API (Retrofit)
- **Domain Layer**: Business logic and use cases
- **UI Layer**: ViewModels, Fragments, Activities
- **Dependency Injection**: Hilt for DI

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM
- **Database**: Room Database
- **Backend**: Firebase (Auth, Firestore, Storage)
- **Network**: Retrofit + OkHttp
- **Dependency Injection**: Hilt
- **Async**: Coroutines + Flow
- **UI**: Material Design 3, Jetpack Compose
- **Logging**: Timber
- **QR Code**: ML Kit
- **Video**: ExoPlayer (Media3)
- **PDF**: android-pdf-viewer

## Project Structure

```
app/src/main/
├── kotlin/com/cadet/tracker/
│   ├── data/
│   │   ├── local/        # Room DAOs and Database
│   │   ├── models/       # Data models and entities
│   │   ├── remote/       # Retrofit API Service
│   │   └── repository/   # Repository implementations
│   ├── ui/
│   │   ├── di/           # Hilt DI modules
│   │   ├── viewmodel/    # ViewModels
│   │   └── MainActivity.kt
│   ├── utils/            # Utility classes
│   └── CadetTrackerApplication.kt
└── res/
    ├── layout/
    ├── values/
    └── drawable/
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog or higher
- Java 8 or higher
- Android SDK 24+
- Gradle 8.1+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/satyamshorrf/CadetTracker.git
cd CadetTracker
```

2. Open in Android Studio

3. Configure Firebase:
   - Create Firebase project at https://console.firebase.google.com
   - Download `google-services.json`
   - Place in `app/` directory

4. Build and run the application

### Configuration

Update the API base URL in `AppModule.kt`:

```kotlin
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://your-api-url/")  // Update this
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
```

## Database Schema

- `users` - User information
- `courses` - Course content
- `attendance` - Attendance records
- `events` - Event information
- `quizzes` - Quiz questions
- `quiz_results` - Quiz submission results
- `camps` - Camp information
- `camp_registrations` - Camp registrations

## Key Components

### Authentication
- Login with email/password or biometric
- Registration with OTP verification
- Firebase authentication integration

### Offline Support
- Local Room database for all data
- Automatic sync when online
- Conflict resolution with server precedence
- Queue system for pending actions

### Data Models
- User (with roles)
- Course (with modules and content)
- Attendance (with location and timestamp)
- Quiz (with questions and results)
- Event (with registration)
- Camp (with registration)

## Dependencies

Key dependencies defined in `build.gradle.kts`:

- AndroidX libraries
- Firebase BOM (Auth, Firestore, Storage)
- Room Database
- Hilt DI
- Retrofit + OkHttp
- Coroutines
- Timber Logging
- Media3 (Video Player)
- ML Kit (QR Code Scanning)

## API Endpoints

The application uses the following API endpoints:

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/verify-otp` - OTP verification

### Users
- `GET /api/users/{uid}` - Get user details
- `PUT /api/users/{uid}` - Update user details

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course details
- `GET /api/courses/category/{category}` - Get courses by category

### Attendance
- `GET /api/attendance/{cadetId}` - Get cadet attendance
- `POST /api/attendance` - Mark attendance
- `GET /api/attendance/date/{date}` - Get attendance by date

### Events
- `GET /api/events` - Get all events
- `GET /api/events/{id}` - Get event details
- `POST /api/events/{id}/register` - Register for event

### Quizzes
- `GET /api/quizzes/{id}` - Get quiz details
- `POST /api/quizzes/{id}/submit` - Submit quiz answers

### Camps
- `GET /api/camps` - Get all camps
- `GET /api/camps/{id}` - Get camp details
- `POST /api/camps/{id}/register` - Register for camp

### Sync
- `POST /api/sync/attendance` - Sync attendance records
- `POST /api/sync/quiz-results` - Sync quiz results
- `POST /api/sync/registrations` - Sync camp registrations

## Future Enhancements

- [ ] Multi-language support
- [ ] Advanced analytics
- [ ] Machine learning-based performance prediction
- [ ] Live video streaming
- [ ] In-app messaging
- [ ] Gamification features
- [ ] Digital signature for certificates
- [ ] Integration with government databases
- [ ] iOS and Web platform support

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and feature requests, please create an issue in the repository.

## Author

Developed by Satya M Shorrf

## Links

- GitHub: https://github.com/satyamshorrf/CadetTracker
- Firebase Console: https://console.firebase.google.com
