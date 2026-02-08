# BRingtone Android App

A modern Android application for creating custom ringtones with audio trimming capabilities.

## 📱 Features

- **Audio File Selection**: Choose audio files from your device storage
- **Audio Player**: Play and preview audio files with seekbar control
- **Audio Trimming**: Trim audio to create custom-length ringtones
- **Set as Ringtone**: Easily set any audio file or trimmed version as your device ringtone
- **Modern UI**: Material Design 3 interface with smooth animations

## 🛠️ Technical Stack

- **Language**: Kotlin
- **Min SDK**: API 26 (Android 8.0)
- **Target SDK**: API 34 (Android 14)
- **Architecture**: Activity-based with helper utilities
- **UI Framework**: Material Design 3
- **Audio Processing**: MediaPlayer API

## 📋 Permissions

The app requires the following permissions:
- `READ_MEDIA_AUDIO` / `READ_EXTERNAL_STORAGE` - To access audio files on device
- `WRITE_SETTINGS` - To set audio files as ringtones

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- JDK 8 or higher
- Android SDK with API 26+

### Building the Project

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run on device or emulator (API 26+)

Alternatively, build from command line:
```bash
cd BRingtone-Simple
./gradlew build
```

### Installing

```bash
./gradlew installDebug
```

## 📖 How to Use

1. **Launch the app** and tap "Select Audio File"
2. **Choose an audio file** from your device
3. **Play and preview** the audio in the player screen
4. **Optional**: Tap "Trim Audio" to customize the length
   - Adjust start and end points using the seekbars
   - Preview the trimmed section
   - Save the trimmed audio
5. **Tap "Set as Ringtone"** to apply as your device ringtone

## 📁 Project Structure

```
BRingtone-Simple/
├── app/
│   ├── src/main/
│   │   ├── java/com/burak/bringtone/
│   │   │   ├── MainActivity.kt           # Main entry point
│   │   │   ├── AudioPlayerActivity.kt    # Audio playback
│   │   │   ├── AudioTrimmerActivity.kt   # Audio trimming
│   │   │   └── RingtoneHelper.kt         # Ringtone utilities
│   │   ├── res/
│   │   │   ├── layout/                   # UI layouts
│   │   │   ├── values/                   # Strings, colors, themes
│   │   │   └── mipmap/                   # App icons
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
└── build.gradle.kts
```

## 🎨 UI Components

- **MainActivity**: File selection interface with Material Design button
- **AudioPlayerActivity**: Audio playback with seekbar, play/pause controls
- **AudioTrimmerActivity**: Dual seekbars for precise trim control

## 🔧 Key Classes

### MainActivity
Handles audio file selection and permission management using modern Android APIs.

### AudioPlayerActivity
Implements MediaPlayer for audio playback with real-time seekbar updates and navigation to trimmer.

### AudioTrimmerActivity
Provides audio trimming interface with preview capability and save functionality.

### RingtoneHelper
Utility class for managing ringtone operations including:
- Permission checking for WRITE_SETTINGS
- Copying audio to ringtone storage
- Setting ringtone via RingtoneManager API

## ⚠️ Known Limitations

- Audio trimming currently copies the full file. For production use, consider implementing actual audio encoding/trimming using FFmpeg or similar libraries.
- Waveform visualization is not implemented (placeholder view only).

## 🔮 Future Enhancements

- [ ] Actual audio trimming with FFmpeg integration
- [ ] Waveform visualization
- [ ] Multiple ringtone management
- [ ] Contact-specific ringtone assignment
- [ ] Audio effects and filters
- [ ] Cloud storage integration

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

**Burak**

---

Built with ❤️ using Kotlin and Material Design 3
