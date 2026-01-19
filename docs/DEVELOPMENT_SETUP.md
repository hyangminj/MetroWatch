# MetroWatch 개발 및 배포 가이드

## 갤럭시 워치에서 테스트하기

### 1. 워치에서 개발자 옵션 활성화

1. **설정** → **워치 정보** → **소프트웨어 정보**
2. **소프트웨어 버전**을 7번 연속 탭
3. "개발자 모드가 활성화되었습니다" 메시지 확인

### 2. ADB 디버깅 활성화

1. **설정** → **개발자 옵션**
2. **ADB 디버깅** 활성화
3. **무선 디버깅** 활성화

### 3. ADB 설치 (macOS)

```bash
# Homebrew로 설치
brew install android-platform-tools

# 설치 확인
adb version
```

### 4. 워치와 페어링

1. 워치에서 **설정** → **개발자 옵션** → **무선 디버깅** 탭
2. **페어링 코드로 기기 페어링** 선택
3. 화면에 표시된 IP:포트와 코드 확인
4. 터미널에서 페어링:

```bash
adb pair <IP주소>:<페어링포트>
# 프롬프트에 페어링 코드 입력
```

5. 페어링 성공 후 연결:

```bash
adb connect <IP주소>:<연결포트>
```

### 5. 앱 설치

#### Android Studio에서 (권장)
1. Android Studio에서 프로젝트 열기
2. 상단 기기 선택에서 워치 선택
3. **Run** 버튼 (▶) 클릭

#### 커맨드라인에서
```bash
# Java 17 필요 (아래는 Homebrew 기준 예시이며, 환경에 따라 경로가 다를 수 있습니다)
export JAVA_HOME=$(brew --prefix openjdk@17)/libexec/openjdk.jdk/Contents/Home

# 빌드 및 설치
./gradlew installDebug
```

### 주의사항

- 워치와 컴퓨터가 **같은 Wi-Fi 네트워크**에 있어야 함
- 워치 화면이 꺼지면 Wi-Fi 연결이 끊길 수 있음
- Java 17 초과 버전(예: Java 21)은 Kotlin/Gradle과 호환성 문제가 있을 수 있음 → Java 17 사용

---

## Play Store 출시하기

### 1. 사전 준비

#### Google Play Console 개발자 계정
- https://play.google.com/console
- $25 1회 결제 후 계정 생성
- 계정 활성화까지 최대 48시간 소요

#### Release AAB 빌드
1. Android Studio에서 **Build** → **Generate Signed Bundle / APK**
2. **Android App Bundle** 선택
3. Keystore 생성 또는 선택
4. **release** 빌드 타입 선택
5. `app/release/app-release.aab` 파일 생성됨

#### 스크린샷 준비
- 워치에서 **전원 버튼 + 뒤로가기 버튼** 동시에 누름
- 최소 1장, 권장 3-5장
- 권장 크기: 384x384 또는 450x450

### 2. Play Console에서 앱 만들기

1. **모든 앱** → **앱 만들기**
2. 앱 정보 입력:
   - 앱 이름: `MetroWatch`
   - 기본 언어: 한국어
   - 앱/게임: 앱
   - 무료/유료: 무료

### 3. 필수 항목 작성

#### 앱 설정 섹션
| 항목 | 설정값 |
|------|--------|
| 앱 액세스 권한 | 특별한 액세스 권한 없이 모든 기능 사용 가능 |
| 광고 | 아니요, 앱에 광고가 포함되어 있지 않습니다 |
| 콘텐츠 등급 | 설문지 작성 → 전체이용가 |
| 타겟층 | 18세 이상 |
| 뉴스 앱 | 아니요 |
| 데이터 보안 | 사용자 데이터 수집 안함 |

#### 스토어 등록정보
| 항목 | 내용 |
|------|------|
| 앱 이름 | MetroWatch |
| 짧은 설명 | 갤럭시 워치를 위한 간편한 메트로놈 - 진동과 소리로 정확한 박자를 제공합니다 |
| 전체 설명 | `store-listing/description-ko.md` 참조 |
| 앱 아이콘 | 512x512 PNG |
| 스크린샷 | 워치 스크린샷 1개 이상 |

### 4. AAB 업로드

1. **프로덕션** → **새 버전 만들기**
2. AAB 파일 업로드
3. 출시 노트 작성
4. **검토 시작**

### 5. 심사 대기

- 보통 며칠 ~ 1주일 소요
- 문제 있으면 이메일로 알림

---

## 참고 자료

- [store-listing/play-store-checklist.md](../store-listing/play-store-checklist.md) - 상세 체크리스트
- [store-listing/screenshot-guide.md](../store-listing/screenshot-guide.md) - 스크린샷 가이드
- [store-listing/description-ko.md](../store-listing/description-ko.md) - 한국어 앱 설명
- [store-listing/description-en.md](../store-listing/description-en.md) - 영어 앱 설명
