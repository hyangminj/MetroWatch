# MetroWatch

갤럭시 워치용 메트로놈 앱입니다. 소리와 진동으로 정확한 비트를 제공합니다.

## 기능

- **BPM 조절**: 40-240 BPM 범위 지원
- **진동 피드백**: 각 비트마다 햅틱 피드백
- **사운드**: 비프음으로 청각적 피드백
- **비트 카운터**: 현재 비트 수 표시
- **Wear OS 최적화**: 갤럭시 워치에 최적화된 UI

## 기술 스택

- **플랫폼**: Wear OS (Android)
- **언어**: Kotlin
- **UI**: Jetpack Compose for Wear OS
- **최소 SDK**: 30 (Android 11)
- **타겟 SDK**: 34

## 프로젝트 구조

```
metroWatch/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/metrowatch/
│   │       │   ├── MainActivity.kt          # 메인 UI
│   │       │   └── MetronomeEngine.kt       # 메트로놈 엔진
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

## 빌드 및 실행

1. **Android Studio 설치**
   - Android Studio Hedgehog (2023.1.1) 이상 권장

2. **프로젝트 열기**
   ```bash
   cd metroWatch
   # Android Studio에서 프로젝트 열기
   ```

3. **Wear OS 에뮬레이터 또는 실제 기기 연결**
   - Tools → Device Manager → Wear OS 기기 추가

4. **빌드 및 실행**
   - Run 버튼 클릭 또는 `Shift + F10`

## 사용법

1. **BPM 조절**
   - `-5`, `-1`, `+1`, `+5` 버튼으로 BPM 조절

2. **메트로놈 시작/정지**
   - `START` 버튼으로 시작
   - `STOP` 버튼으로 정지

3. **비트 확인**
   - 실행 중 현재 비트 수가 화면에 표시됨

## 개발 계획

- [ ] 박자 설정 (2/4, 3/4, 4/4 등)
- [ ] 강세 비트 표시
- [ ] BPM 프리셋 저장
- [ ] 다양한 사운드 옵션
- [ ] 진동 강도 조절

## 라이선스

MIT License
