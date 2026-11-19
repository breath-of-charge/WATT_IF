# WATT IF

**팀명:** 충전의 호흡

**한 줄 소개:** 차량 사용자의 캠핑·차박 준비부터 배터리 관리와 종료 점검까지 지원하는 Pleos Connect 기반 차량용 앱입니다.

## 제출 버전 안내

현재 저장소는 공모전 1차 제출을 위한 실행 가능한 초기 프로토타입입니다. Kotlin과 Jetpack Compose 기반 앱 실행 환경, Room(SQLite) 활성 캠핑 스키마, Flow·StateFlow 기반 비동기 상태 관리를 구성했으며, Vehicle SDK와 NaviHelper SDK의 실제 차량 연동 및 캠핑 기능은 구현 예정 범위입니다.

## 실행 방법

1. Android Studio에서 저장소 루트 폴더를 엽니다.
2. JDK 17 이상과 Android SDK Platform 34를 설정합니다.
3. Gradle Sync를 실행합니다.
4. Android API 34 에뮬레이터 또는 Pleos Connect Emulator에서 `app` 실행 구성을 실행합니다.

명령줄 디버그 빌드:

```powershell
.\gradlew.bat :app:assembleDebug
```

단위 테스트:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

Release 빌드 변형 생성:

```powershell
.\gradlew.bat :app:assembleRelease
```

## 테스트 환경 및 설정

- 운영체제: Windows 11
- Java: Microsoft OpenJDK 17
- Kotlin: 2.2.21
- Android Gradle Plugin: 8.13.2
- Gradle: 8.13
- Android SDK: minSdk 28 / compileSdk 34 / targetSdk 34
- UI: Jetpack Compose + Material 3
- 로컬 저장: Room 2.8.5 + KSP
- 빌드 검증: Debug·Release APK 생성 및 단위 테스트

## Pleos Connect 대상 환경

- Pleos Connect Emulator: 2.1.0
- System Image: Pleos Connect v2.1.0 APIs / Android API 34
- 화면 규격: Pleos Connect v2.1.0 Automotive 기본 가로형 디바이스 프로필(Compose 반응형 레이아웃)
- 권장 설정: Cold boot / RAM 4 GB

> 현재 제출 버전은 Android 빌드와 APK 서명·검증을 완료한 초기 프로토타입이며, Pleos Connect SDK를 통한 실제 차량 데이터·제어 기능은 구현 범위에 포함되지 않습니다.

## 제출 APK

- 파일 위치: `apk/충전의 호흡_WATT_IF_release.apk`
- 빌드 유형: Release
- 버전: 0.1.0 (`versionCode` 1)
- 설치 확인:

```powershell
adb install -r "apk\충전의 호흡_WATT_IF_release.apk"
```

## 계획된 주요 기능

- 캠핑 환경 프리셋
- 목적지 기반 배터리 예산 관리
- 배터리 보호 알림 및 지원 차량의 프리셋 자동 해제
- 캠핑 진행 상태 복원
- 캠핑 종료 및 출발 전 점검
- Room 기반 캠핑 기록과 사용자 통계

세부 데이터 흐름과 구현 경계는 [초기 아키텍처](docs/ARCHITECTURE.md)를 참고해 주세요.

## Pleos SDK 적용 전 확인

Vehicle SDK와 NaviHelper SDK는 Pleos 개발자 콘솔에서 앱 프로젝트를 만든 뒤 공식 문서의 발급 정보와 저장소 설정을 사용해 연결합니다. SDK 접근 권한과 차량별 지원 범위를 확인하기 전까지 차량 제어 및 목적지 기반 배터리 계산은 구현하지 않습니다.

- [Pleos Connect 개발 환경 설정](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator)
- [Pleos 프로젝트 및 SDK 안내](https://document.pleos.ai/docs/pleos-only/create-my-project)

## 개발 문서

- [개발 및 협업 컨벤션](docs/CONVENTION.md)
- [Pleos 차량용 앱 개발 가이드](docs/PLEOS_GUIDE.md)
- [초기 아키텍처](docs/ARCHITECTURE.md)
