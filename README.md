# MCQ Quiz App

A modern Android quiz application built with Kotlin that provides an engaging and interactive quiz experience with streak tracking, animations, and intuitive navigation.

## 📱 Features

### Core Functionality
- **10 Question Quiz**: Loads questions from a remote JSON API
- **Interactive Answer Selection**: Tap options to select answers with visual feedback
- **Auto-Navigation**: Automatically advances to next question after 2 seconds
- **Skip Functionality**: Skip questions with a dedicated button or swipe gesture
- **Streak Tracking**: Tracks consecutive correct answers with visual streak badge
- **Results Screen**: Comprehensive results showing score, statistics, and performance

### UX Enhancements
- **Splash Screen**: Animated launch screen with smooth transitions
- **Swipe Gestures**: Swipe left to skip questions during the quiz
- **Micro-interactions**: Smooth animations for:
  - Question transitions (fade in/slide)
  - Option buttons (staggered entrance animations)
  - Streak badge activation (bounce effect)
  - Result cards (cascading animations)
- **Visual Feedback**: 
  - Correct answers shown in green
  - Wrong answers shown in red
  - Progress indicator shows quiz completion
- **Streak Badge**: 
  - Lights up at 3+ consecutive correct answers
  - Shows flame emoji (🔥) with streak count
  - Animated badge activation

### Technical Highlights
- **Clean Architecture**: Separation of concerns with data, domain, and presentation layers
- **MVVM Pattern**: ViewModel manages UI state and business logic
- **Dependency Injection**: Dagger 2 for dependency management
- **LiveData**: Reactive UI updates
- **Coroutines**: Asynchronous API calls
- **Data Binding**: Two-way binding for efficient UI updates
- **Offline Support**: Caches questions for improved performance

## 🏗️ Architecture

### Project Structure
```
app/
├── feature/
│   └── quiz/
│       ├── data/          # Data sources
│       ├── models/        # Data models
│       ├── repository/    # Repository pattern
│       ├── ui/           
│       │   ├── activity/  # Activities
│       │   └── adapter/   # RecyclerView adapters
│       └── viewmodel/     # ViewModels
├── di/                    # Dependency injection
│   ├── component/        # Dagger components
│   └── module/          # Dagger modules
└── network/              # API services

### Layer Responsibilities

#### 1. Data Layer
- **QuizDataSource**: Handles API communication
- **QuizRepository**: Manages data operations and caching
- **Models**: Question, QuizState, QuizResult

#### 2. Domain Layer
- **QuizViewModel**: Business logic, state management, streak tracking

#### 3. Presentation Layer
- **SplashActivity**: Animated splash screen
- **QuizActivity**: Main quiz interface with gestures
- **ResultActivity**: Results and statistics display

## 🔄 Quiz Flow

1. **Launch**: App shows animated splash screen
2. **Load Questions**: Fetches 10 questions from API
3. **Question Display**: Shows question with 4 options
4. **Answer Selection**: 
   - User taps an option
   - Shows correct/wrong answer
   - Auto-advances after 2 seconds
5. **Streak Logic**:
   - Tracks consecutive correct answers
   - Badge activates at streak of 3+
   - Resets on wrong answer or skip
6. **Results**: After 10 questions, shows comprehensive results
7. **Restart**: Option to restart quiz from results screen

## 📊 Data Flow

```
API -> DataSource -> Repository -> ViewModel -> UI
```

### State Management
```kotlin
sealed class QuizUiState {
    object Loading
    data class QuizInProgress(...)
    data class QuizCompleted(result: QuizResult)
    data class Error(message: String)
}
```

## 🎨 UI/UX Design

### Color Scheme
- Primary: #2196F3 (Blue)
- Correct: #4CAF50 (Green)
- Wrong: #F44336 (Red)
- Streak Badge: #FFD700 (Gold)
- Background: #F5F5F5 (Light Gray)

### Animations
1. **Splash Screen**: Logo scale + fade in, progress bar
2. **Question Entry**: Fade in + slide from right
3. **Options**: Staggered entrance (50ms delay each)
4. **Streak Badge**: Bounce animation with scale
5. **Results**: Cascading card animations

### Gestures
- **Swipe Left**: Skip current question
- **Tap**: Select answer option

## 🛠️ Technologies Used

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **DI**: Dagger 2
- **Async**: Coroutines + LiveData
- **UI**: Data Binding, Material Design
- **Networking**: Retrofit + Gson
- **API**: [Quiz Questions JSON](https://gist.githubusercontent.com/abhiramr/c1a7527aba4f5c5cf28dd7f8ab01bd7b/raw/2d6e08d27f6444ba59dc5b66f091e2a6a83dd1a9/quiz.json)

## 📦 Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 35
- Minimum Android API 24 (Android 7.0)

### Build & Run
```bash
# Clone the repository
git clone <repository-url>
cd SampleApp

# Build the project
./gradlew clean assembleDebug

# Install on connected device
./gradlew installDebug

# Or run from Android Studio
# Click "Run" button or press Shift+F10
```

## 📱 Testing

### Manual Testing Checklist
- [ ] Splash screen displays correctly
- [ ] Questions load from API
- [ ] All 4 options display properly
- [ ] Selecting correct answer shows green
- [ ] Selecting wrong answer shows red
- [ ] Auto-navigation works (2 second delay)
- [ ] Skip button advances to next question
- [ ] Swipe left gesture skips question
- [ ] Streak badge activates at 3+ correct
- [ ] Wrong answer resets streak
- [ ] Results screen shows correct statistics
- [ ] Restart button works properly

## 🎯 Future Enhancements

- [ ] Review answers feature
- [ ] Timed questions (optional)
- [ ] Multiple quiz categories
- [ ] Leaderboard
- [ ] Share results on social media
- [ ] Dark mode support
- [ ] Accessibility improvements
- [ ] Unit and UI tests
- [ ] Analytics integration
- [ ] Push notifications for daily quizzes

## 📄 License

This project is created as part of an assignment.

## 👨‍💻 Author

Developed with ❤️ using Android best practices and modern development patterns.

---

## 📝 Implementation Notes

### Key Design Decisions

1. **Single Activity Pattern**: Each screen is a separate activity for simplicity
2. **Sealed Classes**: Used for type-safe state management
3. **Cached Questions**: Repository caches questions to avoid repeated API calls
4. **Parcelable Models**: For efficient data passing between activities
5. **ViewBinding + DataBinding**: Combination for type-safe view access and reactive UI

### API Integration
The app fetches questions from:
```
https://gist.githubusercontent.com/abhiramr/c1a7527aba4f5c5cf28dd7f8ab01bd7b/raw/2d6e08d27f6444ba59dc5b66f091e2a6a83dd1a9/quiz.json
```

### Streak Logic Implementation
```kotlin
- Correct answer: currentStreak++, update longestStreak if needed
- Wrong answer: currentStreak = 0
- Skip: currentStreak = 0
- Badge activation: currentStreak >= 3
```

## 🔧 Troubleshooting

### Common Issues

1. **Build Errors**: Run `./gradlew clean` then rebuild
2. **DI Errors**: Rebuild project to regenerate Dagger components
3. **Network Errors**: Check internet connection and API availability
4. **Layout Issues**: Invalidate caches and restart Android Studio

---

**Note**: This is a modern, production-ready quiz application demonstrating Android best practices, clean architecture, and excellent user experience.

