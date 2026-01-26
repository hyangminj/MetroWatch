# Play Store Submission Guide - MetroWatch

A comprehensive step-by-step guide to submit MetroWatch to Google Play Store.

---

## Table of Contents

1. [Prerequisites Checklist](#prerequisites-checklist)
2. [Google Play Console Account Setup](#google-play-console-account-setup)
3. [Creating the App in Play Console](#creating-the-app-in-play-console)
4. [Store Listing Setup](#store-listing-setup)
5. [Uploading the AAB File](#uploading-the-aab-file)
6. [Content Rating](#content-rating)
7. [Data Safety Form](#data-safety-form)
8. [App Access](#app-access)
9. [Advertising Declaration](#advertising-declaration)
10. [Target Audience](#target-audience)
11. [Review and Publish](#review-and-publish)
12. [Expected Review Timeline](#expected-review-timeline)
13. [After Publishing](#after-publishing)

---

## Prerequisites Checklist

Before starting the submission process, ensure you have:

### Required Files
- [ ] **Signed AAB file**: `app/release/app-release.aab` (5.8MB)
- [ ] **App icon**: 512x512px PNG, 32-bit with alpha channel
- [ ] **Screenshots**: At least 1 (recommended 3-5), 384x384px or 450x450px
- [ ] **Keystore file**: Safely backed up (you'll need this for future updates)

### Required Information
- [ ] **Google Account**: For Play Console access
- [ ] **$25 registration fee**: One-time payment for developer account
- [ ] **Contact email**: For Play Store communication
- [ ] **App descriptions**: Available in `store-listing/description-ko.md` and `description-en.md`

### App Details
- **App Name**: MetroWatch
- **Package Name**: com.metrowatch
- **Version Code**: 1
- **Version Name**: 1.0
- **Min SDK**: 30 (Android 11)
- **Target SDK**: 34
- **Category**: Music & Audio

---

## Google Play Console Account Setup

### Step 1: Create Developer Account

1. Go to [Google Play Console](https://play.google.com/console)
2. Sign in with your Google Account
3. Read and accept the **Developer Distribution Agreement**
4. Pay the **$25 one-time registration fee** using credit/debit card
5. Complete your account profile:
   - **Developer name**: Hyangmin Jeong (or your preferred name)
   - **Email address**: Your contact email
   - **Phone number**: Optional but recommended
   - **Website**: https://github.com/hyangminj/MetroWatch

### Step 2: Verify Your Account

1. Check your email for verification link
2. Click the verification link
3. Wait for account approval (usually instant, may take up to 48 hours)

**Note**: Keep your developer account credentials secure. You'll need them for all future app updates.

---

## Creating the App in Play Console

### Step 1: Create New App

1. In Play Console, click **"All apps"** in the left sidebar
2. Click **"Create app"** button (top right)
3. Fill in the app creation form:

| Field | Value |
|-------|-------|
| **App name** | MetroWatch |
| **Default language** | English (United States) or Korean (한국어) |
| **App or game** | App |
| **Free or paid** | Free |

4. Check the declarations:
   - [ ] "I declare that this app complies with Google Play's Developer Program Policies"
   - [ ] "I declare that this app complies with US export laws"

5. Click **"Create app"**

### Step 2: Navigate Dashboard

After creation, you'll see the Dashboard with required tasks. We'll complete each section below.

---

## Store Listing Setup

### Step 1: App Details

Navigate to **"Store presence" → "Main store listing"** in the left sidebar.

#### App Name
```
MetroWatch
```

#### Short Description (80 characters max)

**English:**
```
Simple metronome for Galaxy Watch - Accurate beats with vibration and sound
```

**Korean (if adding Korean locale):**
```
갤럭시 워치를 위한 간편한 메트로놈 - 진동과 소리로 정확한 박자를 제공합니다
```

#### Full Description (4000 characters max)

**English:**
```
MetroWatch - The Precision Metronome on Your Wrist

Whether you're in the practice room or on stage, all you need is your Galaxy Watch for perfect timing. MetroWatch is a Wear OS metronome app specially designed for musicians and dancers.

✨ Key Features

🎵 Precise Tempo
• 40-240 BPM range
• Fine adjustment by 1 BPM
• Quick adjustment by 5 BPM

🎼 Multiple Time Signatures
• 2/4, 3/4, 4/4, 6/8 time signatures
• Easy switching with a single tap
• Accent on the first beat (stronger vibration + higher pitch)

📳 Vibration + Sound Feedback
• Precise haptic feedback on your wrist
• Clear beep sounds
• Independent volume control (0-100%)
• Use sound only, vibration only, or both

⌚ Wear OS Optimized
• Optimized UI for round watch faces
• Simple touch controls
• Clean scrollable layout
• Dark theme for battery efficiency

📊 Beat Counter
• Real-time beat display (e.g., 1/4, 2/4, 3/4, 4/4)
• First beat highlighted in green
• Large, easy-to-read text

🎯 Perfect For
• Musicians practicing instruments
• Dancers rehearsing choreography
• Vocalists working on timing
• Anyone needing rhythm training

💡 Usage Tips
• Use vibration only for silent practice
• Use sound only to share with others
• Tap time signature to quickly change (2/4 → 3/4 → 4/4 → 6/8)

🔋 Lightweight & Efficient
• App size: ~6MB
• Battery efficient
• No internet required
• No ads

Keep perfect time with MetroWatch - simple yet powerful!

---

Developer Contact
For questions or suggestions, please visit:
https://github.com/hyangminj/MetroWatch
```

**Korean (copy from `store-listing/description-ko.md`):**
```
MetroWatch - 손목 위의 정확한 메트로놈

연습실, 공연장, 어디서나 갤럭시 워치만 있으면 정확한 박자를 느낄 수 있습니다. MetroWatch는 음악가와 댄서를 위해 특별히 설계된 Wear OS 메트로놈 앱입니다.

[... rest of Korean description ...]
```

### Step 2: Graphics Assets

#### App Icon (Required)
- **Size**: 512 x 512 pixels
- **Format**: 32-bit PNG with alpha channel
- **File**: Upload your current MetroWatch icon (metronome shape with blue/green/white colors on dark background)
- **Note**: Icon must be a PNG file, not JPEG

#### Feature Graphic (Recommended)
- **Size**: 1024 x 500 pixels
- **Format**: PNG or JPEG
- **Optional but recommended**: Creates a banner at top of Play Store listing
- **Tip**: You can create this later and update

#### Screenshots (Required - Wear OS)

**Minimum Requirements:**
- At least 1 screenshot required
- Format: PNG or JPEG (24-bit, no alpha)
- Minimum size: 320px
- Maximum size: 3840px
- **Recommended size**: 384x384px (Small Round) or 450x450px (Large Round)

**Required Screenshots:**

1. **Main Screen** (Must have)
   - Shows: 120 BPM, 4/4 time signature, START button
   - Shows: BPM control buttons (-5, -1, +1, +5)
   - State: Before starting

2. **Running Screen** (Must have)
   - Shows: Green 120 BPM (running state)
   - Shows: Beat counter (1/4, 2/4, etc.)
   - Shows: Red STOP button

**Recommended Additional Screenshots:**

3. **Volume Control Screen**
   - Shows: Sound volume control (80%)
   - Shows: Vibration intensity control (80%)
   - Shows: +/- adjustment buttons

4. **Different Time Signature**
   - Shows: 3/4 or 6/8 time signature
   - Demonstrates tap-to-change functionality

5. **High BPM Example**
   - Shows: 180 BPM or higher
   - Demonstrates range capability

**How to capture screenshots:**
See `store-listing/screenshot-guide.md` for detailed instructions.

### Step 3: Categorization

Navigate to **"Store settings" → "App category"**

| Field | Value |
|-------|-------|
| **App category** | Music & Audio |
| **Tags** | metronome, music, practice, rhythm, timing, wear os |

### Step 4: Contact Details

Navigate to **"Store settings" → "Contact details"**

| Field | Value |
|-------|-------|
| **Email** | Your contact email |
| **Phone** | Optional |
| **Website** | https://github.com/hyangminj/MetroWatch |

Click **"Save"** at the bottom of each section.

---

## Uploading the AAB File

### Step 1: Create Production Release

1. Navigate to **"Release" → "Production"** in left sidebar
2. Click **"Create new release"** button

### Step 2: Upload AAB

1. Click **"Upload"** in the "App bundles" section
2. Select your signed AAB file: `app/release/app-release.aab`
3. Wait for upload to complete (may take 1-2 minutes)
4. Google Play will process the AAB and show you supported devices

### Step 3: Release Name

The release name is auto-filled from your version:
```
1 (1.0)
```

### Step 4: Release Notes

Add release notes for this version. Use both English and Korean if supporting both languages.

**English:**
```
Initial release of MetroWatch! 🎵

Features:
• Precise metronome with 40-240 BPM range
• Multiple time signatures (2/4, 3/4, 4/4, 6/8)
• Haptic and audio feedback
• Independent volume controls
• Optimized for Wear OS round displays
• No ads, no internet required

Perfect for musicians, dancers, and anyone who needs accurate timing!
```

**Korean:**
```
MetroWatch 첫 출시! 🎵

주요 기능:
• 정확한 메트로놈 (40-240 BPM)
• 다양한 박자 지원 (2/4, 3/4, 4/4, 6/8)
• 진동 + 소리 피드백
• 독립적인 볼륨 조절
• Wear OS 원형 화면 최적화
• 광고 없음, 인터넷 불필요

음악가, 댄서, 정확한 타이밍이 필요한 모든 분께 추천합니다!
```

### Step 5: Review Summary

Review the rollout summary:
- Countries/regions: All countries (or select specific ones)
- Rollout percentage: 100% (or staged rollout like 10%, 50%, 100%)

Click **"Save"** (don't publish yet - we need to complete other sections first).

---

## Content Rating

Content rating is required to publish. This questionnaire determines age ratings across different regions.

### Step 1: Start Questionnaire

1. Navigate to **"Policy" → "App content" → "Content ratings"**
2. Click **"Start questionnaire"**
3. Enter your email address (for certificate)

### Step 2: Category Selection

| Question | Answer |
|----------|--------|
| **Select a category** | Music & Audio |

### Step 3: Answer Questions

For MetroWatch, answer as follows:

**Violence:**
- Does your app contain violence? **No**
- Does your app contain realistic violence? **No**
- Does your app contain blood or gore? **No**

**Sexual Content:**
- Does your app contain sexual content? **No**
- Does your app contain nudity? **No**
- Does your app contain sexual themes? **No**

**Language:**
- Does your app contain profanity? **No**
- Does your app contain crude humor? **No**

**Controlled Substances:**
- Does your app reference or depict drugs, alcohol, or tobacco? **No**

**Gambling:**
- Does your app contain simulated gambling? **No**
- Does your app allow users to gamble with real money? **No**

**User Interaction:**
- Do users interact with each other in your app? **No**
- Can users share their location with other users? **No**
- Does your app contain ads? **No**

**Data Collection:**
- Does your app collect any user data? **No**

### Step 4: Submit

1. Review your answers
2. Click **"Submit"**
3. You'll receive content rating certificates for different regions:
   - **ESRB**: Everyone
   - **PEGI**: 3+
   - **USK**: All ages
   - **ACB**: General
   - **IARC**: 3+

These ratings will be displayed automatically on your Play Store listing.

---

## Data Safety Form

Google requires all apps to declare data collection and privacy practices.

### Step 1: Access Data Safety

1. Navigate to **"Policy" → "App content" → "Data safety"**
2. Click **"Start"**

### Step 2: Data Collection

**Question: Does your app collect or share any of the required user data types?**

**Answer: No**

Select: **"No, our app doesn't collect or share any of the required user data types"**

### Step 3: Data Security Practices

Even though you don't collect data, you must answer security questions:

**Question: Is all of the user data collected by your app encrypted in transit?**

**Answer: N/A** (since no data is collected)

**Question: Do you provide a way for users to request that their data is deleted?**

**Answer: N/A** (since no data is collected)

### Step 4: Privacy Policy

**Question: Do you have a privacy policy?**

**Answer: No** (not required since you don't collect any data)

If Google requires a privacy policy URL, you can create a simple one and host it on GitHub Pages or your website stating:
```
MetroWatch Privacy Policy

MetroWatch does not collect, store, or share any user data.
The app operates entirely on your device and does not require internet access.
No personal information, usage data, or analytics are collected.
```

### Step 5: Submit

Click **"Submit"** to save your data safety declarations.

---

## App Access

### Step 1: Navigate to App Access

1. Navigate to **"Policy" → "App content" → "App access"**
2. Click **"Start"**

### Step 2: Special Access

**Question: Does your app require any special access or credentials to use all features?**

**Answer: No**

Select: **"No, my app does not require special access"**

This confirms that anyone can download and use MetroWatch without:
- Login credentials
- Special device configurations
- Premium accounts
- Geographic restrictions

### Step 3: Save

Click **"Save"** to continue.

---

## Advertising Declaration

### Step 1: Navigate to Ads

1. Navigate to **"Policy" → "App content" → "Ads"**
2. Click **"Start"**

### Step 2: Ad Declaration

**Question: Does your app contain ads?**

**Answer: No**

Select: **"No, my app does not contain ads"**

### Step 3: Save

Click **"Save"**.

This ensures your app is marked as ad-free in the Play Store.

---

## Target Audience

### Step 1: Navigate to Target Audience

1. Navigate to **"Policy" → "App content" → "Target audience and content"**
2. Click **"Start"**

### Step 2: Target Age Groups

**Question: What age groups is your app targeting?**

**Answer: Select all ages**

Check all boxes:
- [ ] Ages 5 and under
- [ ] Ages 6-8
- [ ] Ages 9-12
- [x] **Ages 13-17** (primary)
- [x] **Ages 18+** (primary)

**Note**: While primarily for adults (musicians), the app is safe for all ages.

### Step 3: Store Presence

**Question: Do you want your app to be part of the Designed for Families program?**

**Answer: No**

MetroWatch is not specifically designed for children, so select **"No"**.

### Step 4: App Details

**Question: Does your app appeal primarily to children?**

**Answer: No**

MetroWatch is designed for musicians and dancers of all ages.

### Step 5: Save

Click **"Save"** to continue.

---

## Review and Publish

### Step 1: Complete All Required Sections

Before publishing, ensure all sections are completed. Check the Dashboard for any remaining tasks:

- [x] Store listing
- [x] App content (Content rating, Data safety, App access, Ads, Target audience)
- [x] Production release (AAB uploaded)
- [x] Pricing & distribution (set below)

### Step 2: Pricing & Distribution

1. Navigate to **"Grow" → "Pricing & distribution"**
2. Fill in:

| Field | Value |
|-------|-------|
| **App pricing** | Free |
| **Countries** | Select "Add countries/regions" → "Select all" (or choose specific countries) |
| **Contains ads** | No |
| **Designed for Families** | No |
| **App access** | Public |

3. **Distribution channels:**
   - [x] Google Play for Wear OS

4. **Consent:**
   - [x] I acknowledge that my app meets the Android Wear guidelines
   - [x] I confirm that my app complies with all applicable laws

5. Click **"Save"**

### Step 3: Review Release

1. Go back to **"Release" → "Production"**
2. Review your release that you saved earlier
3. Check all details:
   - AAB file uploaded
   - Release notes added
   - Version code: 1
   - Version name: 1.0

### Step 4: Final Review

1. Click **"Review release"** button
2. Google Play will show you any warnings or errors
3. Common warnings to expect (you can ignore these):
   - "Your app targets API level X, but Android Y is available" (normal)
   - "Consider adding more screenshots" (optional)

### Step 5: Rollout to Production

1. If no blocking errors, click **"Start rollout to Production"**
2. Confirm the rollout
3. Your app is now submitted for review

**Important**: Once submitted, you cannot edit:
- The AAB file
- Store listing (until approved)
- Content declarations (until approved)

---

## Expected Review Timeline

### Review Process

After submission, Google Play will review your app:

**Timeline:**
- **Typical**: 1-3 days
- **Maximum**: Up to 7 days
- **Peak periods** (holidays): May take longer

**Review Status:**

You can check status at **"Release" → "Production"**:
- **Pending publication**: Under review
- **Changes requested**: Google found issues (check email)
- **Published**: Live on Play Store

### What Google Reviews

1. **Policy compliance**:
   - Content ratings accuracy
   - Data safety declarations
   - No violations of Developer Program Policies

2. **App quality**:
   - App installs and runs properly
   - No crashes on startup
   - Core functionality works as described

3. **Metadata accuracy**:
   - Screenshots match actual app
   - Description matches app functionality
   - No misleading claims

### If Rejected

If your app is rejected:

1. Check your email for rejection reason
2. Review **"Policy status"** in Play Console
3. Fix the issues
4. Create a new release with fixes
5. Resubmit for review

**Common rejection reasons:**
- Misleading description or screenshots
- Broken core functionality
- Missing required permissions declarations
- Content rating mismatch

### After Approval

Once approved:
- You'll receive an email confirmation
- App appears on Play Store within hours
- You can search for "MetroWatch" on Play Store
- Share the link: `https://play.google.com/store/apps/details?id=com.metrowatch`

---

## After Publishing

### Monitor Your App

1. **Play Console Dashboard**:
   - Check daily active users
   - Monitor crash reports (**"Quality" → "Android vitals"**)
   - Read user reviews (**"Grow" → "Ratings and reviews"**)

2. **Respond to Reviews**:
   - Reply to user feedback
   - Address bug reports
   - Thank positive reviewers

3. **Track Performance**:
   - Downloads count
   - Ratings (average out of 5 stars)
   - Device compatibility issues

### Update Your App

When you need to release updates:

1. Increment version in `app/build.gradle.kts`:
   ```kotlin
   versionCode = 2
   versionName = "1.1"
   ```

2. Build new signed AAB
3. Go to **"Release" → "Production" → "Create new release"**
4. Upload new AAB
5. Add release notes
6. Click **"Review release"** → **"Start rollout to Production"**

Updates are usually reviewed faster (1-2 days).

### Promote Your App

1. **Update GitHub README**:
   - Add Play Store badge
   - Include download link
   - Add screenshots

2. **Share on social media** (optional):
   - Twitter/X
   - Reddit (r/wearos, r/musicproduction)
   - Facebook music groups

3. **Get initial reviews**:
   - Ask friends/beta testers to rate
   - Request feedback from musician communities
   - Respond to all reviews

### Maintain Your App

- Monitor for Android OS updates
- Update dependencies regularly
- Fix reported bugs promptly
- Consider user feature requests
- Keep content ratings current

---

## Troubleshooting

### Common Issues

**Issue: "Upload failed - Bundle signature mismatch"**
- **Solution**: You're using a different keystore. Use the same keystore you used for the first upload.

**Issue: "Duplicate app on Play Store"**
- **Solution**: Package name `com.metrowatch` must be unique. If taken, change in `app/build.gradle.kts`.

**Issue: "Screenshots don't meet requirements"**
- **Solution**: Ensure screenshots are:
  - At least 320px
  - PNG or JPEG format
  - Actual app screenshots (not mockups)

**Issue: "Data safety form rejected"**
- **Solution**: Be honest about data collection. If you collect nothing, select "No data collected."

**Issue: "Content rating requires age 13+"**
- **Solution**: Review questionnaire answers. Metronome apps should get "Everyone" rating if answered correctly.

### Getting Help

- **Google Play Console Help**: Click "?" icon in top right
- **Developer Support**: https://support.google.com/googleplay/android-developer
- **Policy Questions**: https://support.google.com/googleplay/android-developer/answer/9859455
- **Community Forums**: https://www.reddit.com/r/androiddev

---

## Quick Reference Checklist

Use this final checklist before submission:

### Pre-Submission
- [ ] Developer account created and verified ($25 paid)
- [ ] Signed AAB file ready (`app/release/app-release.aab`)
- [ ] Keystore backed up securely
- [ ] App icon 512x512 PNG ready
- [ ] At least 1 screenshot ready (recommended 3-5)
- [ ] Contact email ready

### Play Console Setup
- [ ] App created with name "MetroWatch"
- [ ] Store listing completed (short + full description)
- [ ] Graphics uploaded (icon + screenshots)
- [ ] Category set to "Music & Audio"
- [ ] Contact details added

### Policy & Content
- [ ] Content rating questionnaire completed
- [ ] Data safety form submitted ("No data collected")
- [ ] App access declared (no special access required)
- [ ] Ads declaration completed (no ads)
- [ ] Target audience set (all ages, not for children primarily)

### Release
- [ ] AAB uploaded to Production
- [ ] Release notes added (English/Korean)
- [ ] Pricing set to Free
- [ ] Countries selected (all or specific)
- [ ] All required tasks completed (check Dashboard)

### Final Steps
- [ ] Review release summary
- [ ] Click "Start rollout to Production"
- [ ] Confirm submission
- [ ] Wait for email notification (1-7 days)

---

## File Locations Reference

For easy access during submission:

| Asset | Location |
|-------|----------|
| **AAB file** | `/Users/hyangmin/Downloads/github/MetroWatch/app/release/app-release.aab` |
| **English description** | `/Users/hyangmin/Downloads/github/MetroWatch/store-listing/description-en.md` |
| **Korean description** | `/Users/hyangmin/Downloads/github/MetroWatch/store-listing/description-ko.md` |
| **Screenshot guide** | `/Users/hyangmin/Downloads/github/MetroWatch/store-listing/screenshot-guide.md` |
| **Play Store checklist** | `/Users/hyangmin/Downloads/github/MetroWatch/store-listing/play-store-checklist.md` |

---

## Support

**Developer**: Hyangmin Jeong
**GitHub**: https://github.com/hyangminj/MetroWatch
**Issues**: https://github.com/hyangminj/MetroWatch/issues

For Play Store-specific questions:
- Google Play Console Help Center
- Android Developer Community

---

**Good luck with your submission!** 🚀

Once MetroWatch is live, share the Play Store link with musicians and watch your downloads grow!
