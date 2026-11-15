# WATT IF

현대자동차그룹 차량용 앱 개발 공모전을 위한 Pleos Connect 기반 차박·캠핑 관리 앱입니다.

현재 저장소는 **기능 구현 전 초기 환경**입니다. Kotlin과 Jetpack Compose로 실행 가능한 최소 앱을 구성했고, 기획서에 명시된 Room(SQLite), Vehicle SDK, NaviHelper SDK 연동 지점은 [초기 아키텍처](docs/ARCHITECTURE.md)에 정리했습니다.

## 개발 기준

- Kotlin 2.2.21
- Jetpack Compose + Material 3
- Android Gradle Plugin 8.13.2 / Gradle 8.13
- Java 17
- minSdk 28 / compileSdk 34 / targetSdk 34
- Room 2.6.1 + KSP (SQLite 영속성 계층; 스키마와 DAO는 기능 구현 단계에서 추가)

## 처음 실행하기

1. Android Studio에서 이 폴더를 엽니다.
2. JDK는 Android Studio 내장 JDK 17 이상을 선택합니다.
3. SDK Manager에서 Android SDK Platform 34와 Build Tools 34.0.0을 설치합니다.
4. Gradle Sync 후 `app` 실행 구성을 선택합니다.
5. Pleos Connect Emulator API 34 시스템 이미지에서 실행합니다.

명령줄 빌드:

```powershell
.\gradlew.bat :app:assembleDebug
```

단위 테스트:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

디버그 APK는 `app/build/outputs/apk/debug/app-debug.apk`에 생성됩니다.

## Pleos SDK 적용 전 확인

Vehicle SDK와 NaviHelper SDK는 Pleos 개발자 콘솔에서 앱 프로젝트를 만든 뒤 공식 문서의 발급 정보와 저장소 설정을 사용해 연결합니다. SDK 접근 권한과 차량별 지원 범위를 확인하기 전까지 차량 제어 및 목적지 기반 배터리 계산은 구현하지 않습니다.

- [Pleos Connect 개발 환경 설정](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator)
- [Pleos 프로젝트 및 SDK 안내](https://document.pleos.ai/docs/pleos-only/create-my-project)
