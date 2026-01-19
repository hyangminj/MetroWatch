# MetroWatch Development and Deployment Guide

## Testing on Galaxy Watch

### 1. Enable Developer Options on Watch

1. **Settings** → **About watch** → **Software information**
2. Tap **Software version** 7 times consecutively
3. Confirm "Developer mode has been enabled" message

### 2. Enable ADB Debugging

1. **Settings** → **Developer options**
2. Enable **ADB debugging**
3. Enable **Wireless debugging**

### 3. Install ADB (macOS)

```bash
# Install via Homebrew
brew install android-platform-tools

# Verify installation
adb version
```

### 4. Pair with Watch

1. On watch: **Settings** → **Developer options** → Tap **Wireless debugging**
2. Select **Pair device with pairing code**
3. Note the IP:port and pairing code displayed
4. Pair from terminal:

```bash
adb pair <IP_ADDRESS>:<PAIRING_PORT>
# Enter pairing code when prompted
```

5. After successful pairing, connect:

```bash
adb connect <IP_ADDRESS>:<CONNECTION_PORT>
```

### 5. Install App

#### From Android Studio (Recommended)
1. Open project in Android Studio
2. Select watch from device dropdown
3. Click **Run** button (▶)

#### From Command Line
```bash
# Java 17 required (example path for Homebrew, may vary by environment)
export JAVA_HOME=$(brew --prefix openjdk@17)/libexec/openjdk.jdk/Contents/Home

# Build and install
./gradlew installDebug
```

### Notes

- Watch and computer must be on the **same Wi-Fi network**
- Wi-Fi connection may drop when watch screen turns off
- Java versions above 17 (e.g., Java 21) may have compatibility issues with Kotlin/Gradle → Use Java 17

---

## Publishing to Play Store

### 1. Prerequisites

#### Google Play Console Developer Account
- https://play.google.com/console
- One-time $25 registration fee
- Account activation may take up to 48 hours

#### Build Release AAB
1. In Android Studio: **Build** → **Generate Signed Bundle / APK**
2. Select **Android App Bundle**
3. Create or select Keystore
4. Select **release** build type
5. `app/release/app-release.aab` file will be generated

#### Prepare Screenshots
- On watch: Press **Power button + Back button** simultaneously
- Minimum 1, recommended 3-5 screenshots
- Recommended size: 384x384 or 450x450

### 2. Create App in Play Console

1. **All apps** → **Create app**
2. Enter app information:
   - App name: `MetroWatch`
   - Default language: Your preference
   - App or game: App
   - Free or paid: Free

### 3. Complete Required Sections

#### App Settings Section
| Item | Setting |
|------|---------|
| App access | All functionality available without special access |
| Ads | No, this app does not contain ads |
| Content rating | Complete questionnaire → Everyone |
| Target audience | 18 and over |
| News app | No |
| Data safety | No user data collected |

#### Store Listing
| Item | Content |
|------|---------|
| App name | MetroWatch |
| Short description | Simple metronome for Galaxy Watch - Accurate beats with vibration and sound |
| Full description | See `store-listing/description-en.md` |
| App icon | 512x512 PNG |
| Screenshots | At least 1 watch screenshot |

### 4. Upload AAB

1. **Production** → **Create new release**
2. Upload AAB file
3. Write release notes
4. **Start review**

### 5. Wait for Review

- Usually takes a few days to 1 week
- Issues will be notified via email

---

## References

- [store-listing/play-store-checklist.md](../store-listing/play-store-checklist.md) - Detailed checklist
- [store-listing/screenshot-guide.md](../store-listing/screenshot-guide.md) - Screenshot guide
- [store-listing/description-ko.md](../store-listing/description-ko.md) - Korean app description
- [store-listing/description-en.md](../store-listing/description-en.md) - English app description
