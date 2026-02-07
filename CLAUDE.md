# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing config in local.properties)
./gradlew assembleRelease

# Install to connected Wear OS device/emulator
./gradlew installDebug

# Clean build
./gradlew clean
```

Release signing requires these properties in `local.properties`:
`RELEASE_STORE_FILE`, `RELEASE_STORE_PASSWORD`, `RELEASE_KEY_ALIAS`, `RELEASE_KEY_PASSWORD`

## Project Overview

MetroWatch is a standalone metronome app for Wear OS (Galaxy Watch). It provides audio and haptic feedback for musicians. Single-module Android app with no tests or CI.

**Tech Stack:**
- Kotlin 2.2.10 with Jetpack Compose for Wear OS
- Android Gradle Plugin 9.0.0, Gradle 9.1.0
- Min SDK 30, Target/Compile SDK 35
- Java 17 compatibility

## Architecture

Two Kotlin files in `app/src/main/java/com/metrowatch/`:

### MetronomeEngine (`MetronomeEngine.kt`)
Core timing and feedback logic:
- `ToneGenerator` for audio (accent: `TONE_CDMA_ALERT_CALL_GUARD`, normal: `TONE_PROP_BEEP`)
- `Vibrator` with `VibrationEffect` for haptic feedback (accent: 80ms/255 amplitude, normal: 50ms/180 amplitude, both scaled by user intensity setting)
- Coroutine-based beat loop on `Dispatchers.Default` with configurable BPM (40-240)
- State exposed via `StateFlow`: `isRunning`, `bpm`, `beatCount`, `timeSignature`, `soundVolume`, `vibrationIntensity`
- `TimeSignature` enum: TWO_FOUR, THREE_FOUR, FOUR_FOUR, SIX_EIGHT
- Sound volume changes recreate the `ToneGenerator` instance

### MainActivity (`MainActivity.kt`)
Compose UI and theme in a single file:
- `MetroWatchTheme` wraps content with primary color `#1E88E5` on black background
- `MetronomeScreen` uses `ScalingLazyColumn` for round watch face scrolling
- `MetronomeEngine` is created directly in `onCreate()` (not via ViewModel) and released in `onDestroy()`
- UI labels are hardcoded in Korean (박자, 소리, 진동)

## Key Patterns

- All metronome state lives in `MetronomeEngine` using `MutableStateFlow`/`StateFlow`
- UI observes state via `collectAsState()` in Compose
- Beat timing: `delay(60000 / bpm)` in a coroutine loop
- Accent beat detection: `beatCount % beatsPerMeasure == 0`
- BPM adjustments: ±1 and ±5 buttons; volume/vibration: ±10% buttons
- Standalone Wear OS app (no companion phone app required)
