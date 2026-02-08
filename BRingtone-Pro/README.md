# BRingtone Pro

A professional Android ringtone management application with advanced audio editing capabilities, waveform visualization, and comprehensive library management.

## 🌟 Features

### Core Features
- 📚 **Ringtone Library Management** - Organize and manage all your custom ringtones
- 🎵 **Advanced Audio Editor** - Edit audio with trim, fade, and normalization effects
- 📊 **Waveform Visualization** - Visual representation of audio for precise editing
- 🎨 **Audio Effects** - Fade in/out, volume normalization, and pitch adjustment
- 📱 **Contact Assignment** - Set personalized ringtones for different contacts (coming soon)
- 🔍 **Search & Filter** - Quickly find ringtones by name or category

### Pro Architecture
- **MVVM Pattern** - Modern architecture with ViewModel and LiveData
- **Room Database** - Persistent storage for ringtone library
- **Navigation Component** - Single-activity architecture with fragment navigation
- **Coroutines** - Asynchronous operations for smooth performance
- **Material Design 3** - Modern, beautiful UI following Google's design guidelines

## 🛠️ Technical Stack

- **Language**: Kotlin
- **Min SDK**: API 26 (Android 8.0)
- **Target SDK**: API 34 (Android 14)
- **Architecture**: MVVM with Repository Pattern
- **Database**: Room 2.6.1
- **UI Framework**: Material Design 3
- **Navigation**: Navigation Component 2.7.6
- **Async**: Kotlin Coroutines 1.7.3

## 📋 Permissions

The app requires the following permissions:
- `READ_MEDIA_AUDIO` / `READ_EXTERNAL_STORAGE` - Access audio files
- `WRITE_SETTINGS` - Set ringtones
- `READ_CONTACTS` / `WRITE_CONTACTS` - Contact-specific ringtones (future feature)

## 🚀 Getting Started

### Prerequisites

- Android Studio (Hedgehog or newer recommended)
- JDK 8 or higher
- Android SDK with API 26+

### Building the Project

1. Clone the repository
2. Open BRingtone-Pro in Android Studio
3. Sync Gradle files
4. Run on device or emulator (API 26+)

Command line build:
```bash
cd BRingtone-Pro
./gradlew build
```

### Installing

```bash
./gradlew installDebug
```

## 📖 How to Use

1. **Library Screen**:
   - View all your ringtones
   - Search and filter by category
   - Tap + button to add new ringtones

2. **Audio Editor**:
   - Trim audio to desired length
   - Apply fade in/out effects
   - Normalize volume
   - Preview changes before saving

3. **Set as Ringtone**:
   - Choose audio from library or editor
   - Apply effects and save
   - Set as device ringtone

## 📁 Project Structure

```
BRingtone-Pro/
├── app/src/main/
│   ├── java/com/burak/bringtonepro/
│   │   ├── data/                    # Room database entities and DAOs
│   │   │   ├── RingtoneEntity.kt
│   │   │   ├── RingtoneDao.kt
│   │   │   └── AppDatabase.kt
│   │   ├── repository/              # Data repository layer
│   │   │   └── RingtoneRepository.kt
│   │   ├── viewmodel/               # ViewModels for UI
│   │   │   ├── RingtoneLibraryViewModel.kt
│   │   │   └── AudioEditorViewModel.kt
│   │   ├── ui/                      # Fragments and UI components
│   │   │   ├── library/
│   │   │   ├── editor/
│   │   │   ├── contacts/
│   │   │   └── settings/
│   │   ├── MainActivity.kt
│   │   └── BRingtoneProApplication.kt
│   └── res/
│       ├── layout/                  # XML layouts
│       ├── navigation/              # Navigation graph
│       └── values/                  # Strings, colors, themes
```

## 🏗️ Architecture

### MVVM Pattern
- **Model**: Room database entities and DAOs
- **ViewModel**: Manages UI state and business logic
- **View**: Fragments with ViewBinding

### Data Flow
```
UI (Fragment) → ViewModel → Repository → Database
              ←            ←            ←
```

## 🎨 UI Components

- **RingtoneLibraryFragment**: Main screen with RecyclerView, search, and filters
- **AudioEditorFragment**: Advanced editing with waveform and effects
- **ContactAssignmentFragment**: Contact-specific ringtone assignment (placeholder)
- **SettingsFragment**: App preferences and settings (placeholder)

## 🔮 Upcoming Features

- [ ] Actual waveform rendering with audio analysis
- [ ] Real-time audio effects preview
- [ ] FFmpeg integration for true audio trimming
- [ ] Contact-specific ringtone assignment
- [ ] Cloud backup and sync
- [ ] Multiple audio format export
- [ ] Batch operations on ringtones
- [ ] Dark mode support

## 📊 Comparison with BRingtone-Simple

| Feature | Simple | Pro |
|---------|--------|-----|
| Basic playback | ✅ | ✅ |
| Set as ringtone | ✅ | ✅ |
| Trim audio | ✅ | ✅ |
| Audio effects | ❌ | ✅ |
| Waveform visualization | ❌ | ✅ |
| Library management | ❌ | ✅ |
| Room database | ❌ | ✅ |
| MVVM architecture | ❌ | ✅ |
| Navigation Component | ❌ | ✅ |
| Search & filter | ❌ | ✅ |

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

**Burak**

---

Built with ❤️ using Kotlin, Room, Navigation Component, and Material Design 3
