# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install to connected Wear OS device/emulator
./gradlew installDebug

# Clean build
./gradlew clean
```

## Project Overview

MetroWatch is a metronome app for Wear OS (Galaxy Watch). It provides audio and haptic feedback for musicians.

**Tech Stack:**
- Kotlin with Jetpack Compose for Wear OS
- Min SDK 30 (Android 11), Target SDK 34
- Kotlin Coroutines for timing

## Architecture

The app has two main components:

### MetronomeEngine (`app/src/main/java/com/metrowatch/MetronomeEngine.kt`)
Core timing and feedback logic:
- Uses `ToneGenerator` for audio (accent vs normal beat sounds)
- Uses `Vibrator` with `VibrationEffect` for haptic feedback (stronger vibration on accent beats)
- Coroutine-based beat loop with configurable BPM (40-240)
- State exposed via `StateFlow` for reactive UI updates
- Supports time signatures: 2/4, 3/4, 4/4, 6/8

### MainActivity (`app/src/main/java/com/metrowatch/MainActivity.kt`)
Compose UI with:
- `ScalingLazyColumn` for scrollable content on round watch face
- BPM display and control buttons
- Time signature selector (tap to cycle)
- Sound/vibration volume controls
- Start/Stop button

## Key Patterns

- All metronome state is in `MetronomeEngine` using `MutableStateFlow`/`StateFlow`
- UI observes state via `collectAsState()` in Compose
- Beat timing uses `Dispatchers.Default` coroutine with `delay()`
- Accent beat detection: `beatCount % beatsPerMeasure == 0`
