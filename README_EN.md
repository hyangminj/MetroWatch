# MetroWatch

A metronome app for Galaxy Watch. Provides accurate beats with sound and vibration.

## Features

- **BPM Control**: Supports 40-240 BPM range
- **Vibration Feedback**: Haptic feedback on each beat
- **Sound**: Auditory feedback with beep sounds
- **Beat Counter**: Displays current beat count
- **Wear OS Optimized**: UI optimized for Galaxy Watch

## Tech Stack

- **Platform**: Wear OS (Android)
- **Language**: Kotlin
- **UI**: Jetpack Compose for Wear OS
- **Min SDK**: 30 (Android 11)
- **Target SDK**: 34

## Project Structure

```
metroWatch/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/metrowatch/
│   │       │   ├── MainActivity.kt          # Main UI
│   │       │   └── MetronomeEngine.kt       # Metronome engine
│   │       ├── res/
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Build and Run

1. **Install Android Studio**
   - Android Studio Hedgehog (2023.1.1) or later recommended

2. **Open Project**
   ```bash
   cd metroWatch
   # Open project in Android Studio
   ```

3. **Connect Wear OS Emulator or Physical Device**
   - Tools → Device Manager → Add Wear OS device

4. **Build and Run**
   - Click Run button or press `Shift + F10`

## Usage

1. **Adjust BPM**
   - Use `-5`, `-1`, `+1`, `+5` buttons to adjust BPM

2. **Start/Stop Metronome**
   - Press `START` button to start
   - Press `STOP` button to stop

3. **Check Beat**
   - Current beat count is displayed while running

## Development Roadmap

- [x] Time signature settings (2/4, 3/4, 4/4, 6/8)
- [x] Accent beat indication
- [ ] Save BPM presets
- [ ] Various sound options
- [x] Vibration intensity control

## License

MIT License
