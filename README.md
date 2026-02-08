# BRingtone

Professional Android ringtone applications with audio editing and management capabilities.

## 📱 Applications

This repository contains two Android applications:

### 🎵 BRingtone-Simple
A straightforward ringtone application with essential features.

**Features:**
- Audio file selection and playback
- Basic audio trimming
- Set audio as ringtone
- Clean, minimal interface
- Material Design 3

**Technology:**
- Kotlin
- Activity-based architecture
- MediaPlayer API
- Material Design 3

[View Simple Documentation →](./BRingtone-Simple/README.md)

---

### 🚀 BRingtone-Pro
An advanced, professional ringtone management application with modern architecture.

**Features:**
- 📚 Ringtone library with database storage
- 📊 Waveform visualization
- 🎨 Audio effects (fade in/out, normalize, pitch)
- 🔍 Search and filter capabilities
- 📱 Contact-specific ringtones (coming soon)
- 💾 Persistent storage with Room database

**Technology:**
- Kotlin
- MVVM architecture
- Room Database
- Navigation Component
- Coroutines
- Material Design 3

[View Pro Documentation →](./BRingtone-Pro/README.md)

---

## 🔄 Comparison

| Feature | Simple | Pro |
|---------|:------:|:---:|
| Audio playback | ✅ | ✅ |
| Set as ringtone | ✅ | ✅ |
| Trim audio | ✅ | ✅ |
| Waveform visualization | ❌ | ✅ |
| Audio effects | ❌ | ✅ |
| Library management | ❌ | ✅ |
| Database storage | ❌ | ✅ |
| Search & filter | ❌ | ✅ |
| MVVM architecture | ❌ | ✅ |
| Navigation Component | ❌ | ✅ |

## 🚀 Getting Started

### Prerequisites
- Android Studio (Hedgehog or newer)
- JDK 8+
- Android SDK API 26+

### Building
```bash
# For Simple version
cd BRingtone-Simple
./gradlew build

# For Pro version
cd BRingtone-Pro
./gradlew build
```

### Installation
```bash
# Install Simple
cd BRingtone-Simple
./gradlew installDebug

# Install Pro
cd BRingtone-Pro
./gradlew installDebug
```

## 📋 Requirements

- **Minimum SDK**: API 26 (Android 8.0)
- **Target SDK**: API 34 (Android 14)
- **Language**: Kotlin
- **Build System**: Gradle 8.2

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

Created by Burak

---

**Choose your version:** Start with **Simple** for basic functionality, or jump to **Pro** for advanced features and modern architecture.
