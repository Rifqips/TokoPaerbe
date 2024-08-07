# TokoPaerbe

A brief description of your project and its objectives.

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

## Usage

Guidelines on how to use the application or its key features.

## Contributing

Guidelines for contributing to the project.

## License

MIT License

Copyright (c) 2024 Rifqi Padi Siliwangi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
