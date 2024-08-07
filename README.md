# TokoPaerbe

This project is the final project of the Android trainee program at PT Phincon over a period of 3 months.

## Tech Stack

### Android Jetpack
The project utilizes Android Jetpack components for robust and modern Android app development, including LiveData and ViewModel for managing UI-related data.

### Retrofit & OkHttp
API integration is handled using Retrofit and OkHttp, with JWT token authentication for secure communication.

### LiveData & Flow Coroutines
Employing LiveData and Flow Coroutines for reactive and asynchronous data management.

### MVVM Architecture
The application follows the Model-View-ViewModel (MVVM) architecture to separate business logic from UI concerns.

### Clean Architecture
Implemented Clean Architecture with distinct layers:
- **Datasource**: Manages data sources.
- **Repository**: Handles data retrieval from various sources.
- **Usecase**: Encapsulates business logic for the application.

### Modular
The project is designed with modularity in mind to facilitate easier maintenance and scalability.

### Firebase
Integrated with Firebase for various services:
- **Authentication**: User authentication management.
- **Remote Config**: Remote application configuration.
- **Analytics**: Application analytics tracking.
- **Crashlytics**: Crash reporting and monitoring.
- **Storage**: File storage.
- **Push Notifications**: Push notifications to users.

### Paging 3 & Remote Mediator
Utilizes Paging 3 for efficient data pagination and RemoteMediator for managing data from remote sources.

### Dependency Injection (Koin)
Dependency injection is managed using Koin to simplify dependency management.

### Git (GitLab)
Version control is handled with Git and GitLab for source code management and collaboration.

### UI Testing & Unit Testing
- **UI Testing**: Implemented with JUnit4 for testing user interface components.
- **Unit Testing**: Conducted with Mockito for unit tests.

### Code Quality Tools
- **JaCoCo**: For Java code coverage analysis.
- **Detekt**: For static code analysis and improving code quality.
