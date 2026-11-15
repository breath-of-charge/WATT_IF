# WATT IF 개발 및 협업 컨벤션

빠른 기능 개발을 위한 최소 규칙이다. 기존 단일 `app` 모듈과 패키지명 `com.breathofcharge.wattif`를 유지하며, 이번 초기 설정에서는 Hilt나 멀티 모듈 구조를 도입하지 않는다.

## 기술 스택

| 구분 | 현재 적용 | 개발 기준 또는 예정 |
|---|---|---|
| 언어·빌드 | Kotlin 2.2.21, Gradle Kotlin DSL, JDK 17 | Kotlin 공식 코딩 컨벤션 |
| Android | AGP 8.13.2, Gradle 8.13, minSdk 28, compileSdk·targetSdk 34 | 기존 버전 유지 |
| UI | Jetpack Compose, Material 3 | 화면 상태는 StateFlow로 관리 |
| 구조 | 단일 모듈, MVVM + Repository | 복잡하거나 재사용되는 계산만 domain으로 분리 |
| 로컬 데이터 | Room 2.8.5, SQLite, KSP, Flow | Entity·DAO·Repository 역할 분리 |
| 비동기 | Kotlin Coroutines, Flow·StateFlow | 생명주기를 고려해 수집 |
| 품질 | ktlint, Android Lint, GitHub Actions | CodeRabbit은 GitHub App으로 별도 연결 |
| 차량·내비게이션 | 아직 연동되지 않음 | Pleos Vehicle SDK, NaviHelper SDK 연동 예정 |

별도 백엔드 서버 없이 Android 앱 내부에서 UI, 비즈니스 로직과 로컬 데이터베이스를 관리한다. SDK 의존성이나 차량 권한은 실제 사용 범위가 확정될 때 추가한다.

## 아키텍처

```text
UI (Jetpack Compose)
        ↓
ViewModel
        ↓
Repository
        ↓
Room DB / Vehicle SDK
```

- UI와 데이터 접근 로직을 분리하고 ViewModel에서 화면 상태를 관리한다.
- Room DAO는 Repository를 통해서만 접근한다.
- 복잡하거나 재사용되는 계산은 `domain`으로 분리하고 UseCase는 필요할 때만 만든다.
- SDK 연동 전에는 출처가 명확한 Mock 데이터를 사용하며, 실제 데이터와 혼동되지 않게 표시한다.
- 차량 SDK 접근과 오류 처리는 UI에서 분리한다.

## Kotlin 네이밍과 작성 규칙

| 대상 | 규칙 | 예시 |
|---|---|---|
| 패키지 | lowercase | `battery` |
| 파일·클래스 | PascalCase | `BatteryScreen.kt` |
| Compose 컴포넌트 | PascalCase | `BatteryCard()` |
| ViewModel | PascalCase + ViewModel | `BatteryViewModel` |
| Repository | PascalCase + Repository | `BatteryRepository` |
| Entity | PascalCase + Entity | `CampingSessionEntity` |
| DAO | PascalCase + Dao | `CampingDao` |
| 변수·함수 | camelCase | `batteryLevel` |
| 컴파일 타임 상수 | UPPER_SNAKE_CASE | `MAX_BATTERY_LEVEL` |
| Boolean | is / has / can / should | `isCampingActive` |

- `val`을 기본으로 사용하고 재할당이 필요할 때만 `var`를 사용한다.
- 함수명은 동작과 대상을 드러내며, 들여쓰기는 공백 4칸으로 한다.
- UTF-8과 Kotlin 공식 코딩 컨벤션을 따르고 불필요한 import를 제거한다.

## Jetpack Compose

- `@Composable` 함수는 PascalCase로 작성하고 Screen과 ViewModel 역할을 분리한다.
- 공통 UI는 재사용하며, 재사용 가능한 컴포넌트는 `modifier: Modifier = Modifier`를 제공한다.
- 주요 화면과 공통 컴포넌트에는 `@Preview`를 작성한다.
- 단순 UI 상태는 `remember` 또는 `rememberSaveable`, 화면 단위 상태는 `StateFlow`를 사용한다.
- 필요한 화면은 Loading / Success / Error 상태를 구분한다.
- Composable에서 DB나 차량 SDK에 직접 접근하지 않는다.
- Preview에는 정적 샘플 상태를 주입한다. SDK 객체 생성, 실제 차량 API 호출, DB 연결을 하지 않는다.

## Room DB

- Entity, DAO, Repository 역할을 분리하고 `Entity`, `Dao` 접미사를 통일한다.
- 테이블명은 snake_case를 사용한다.
- 지속 관찰 데이터는 `Flow`, 일회성 비동기 작업은 `suspend`를 사용한다.
- 스키마 변경 시 DB 버전을 올리고, 기존 데이터 보존이 필요하면 Migration을 작성하고 테스트한다.
- 초기 설정을 이유로 사용하지 않을 테이블이나 Migration을 미리 만들지 않는다.

## 패키지 구조 기준

기존 파일을 유지하고 기능 구현 시 필요한 디렉터리만 만든다. 빈 클래스나 빈 폴더는 만들지 않는다.

```text
app/src/main/java/com/breathofcharge/wattif/
├── ui/
│   ├── home/
│   ├── camping/
│   ├── battery/
│   ├── checklist/
│   ├── history/
│   ├── components/
│   ├── navigation/
│   └── theme/
├── domain/
│   └── model/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   └── entity/
│   ├── repository/
│   └── vehicle/
└── MainActivity.kt
```

- 기능별 Screen과 ViewModel은 같은 기능 폴더에 둔다.
- 공통 UI는 `ui/components`, 차량 SDK 접근은 `data/vehicle`에 둔다.
- 기존 패키지명은 임의로 변경하지 않는다.

## Git 브랜치 전략

| 브랜치 | 용도 |
|---|---|
| `main` | 안정적인 제출 버전 |
| `dev` | 개발 통합 브랜치 |
| `init/` | 초기 설정 |
| `feat/` | 기능 구현 |
| `fix/` | 버그 수정 |
| `refactor/` | 리팩토링 |
| `chore/` | 기타 설정 |
| `ci/` | 자동화 |
| `docs/` | 문서 |

브랜치는 최신 `dev`에서 분기하고 PR로 `dev`에 병합한다. `dev`를 운영하지 않는 기간에는 `main`을 기준으로 사용한다. 이름은 `type/이슈번호-기능명` 형식이며 기능명은 kebab-case를 사용한다.

- `init/1-project-setup`
- `feat/12-battery-screen`
- `fix/15-battery-calculation`
- `ci/20-android-ci`

Issue 템플릿의 `브랜치 기능명`을 영문 kebab-case로 작성하고 담당자를 지정하면 작업 브랜치를 자동 생성한다. 저장소 구성원이 작성한 Issue만 처리하며, 같은 이름의 브랜치가 있으면 다시 만들지 않는다.

## 커밋 컨벤션

`type: 작업 내용 (#이슈번호)` 형식으로 한글 설명을 작성한다. 한 커밋에는 가능한 한 하나의 논리적 작업 단위를 담는다.

- 허용 type: `init`, `feat`, `fix`, `refactor`, `chore`, `build`, `ci`, `docs`, `style`, `test`
- 예: `init: 프로젝트 컨벤션 설정 (#1)`
- 예: `feat: 배터리 상태 화면 구현 (#12)`
- 예: `fix: 캠핑 상태 복원 오류 수정 (#15)`

## PR 컨벤션

제목은 `[Type] 작업 내용` 형식으로 작성한다. 예: `[Init] 프로젝트 초기 설정`.

- 하나의 PR에는 가능한 한 하나의 기능을 포함하고 대상 브랜치는 `dev`로 한다.
- 관련 Issue를 연결하고 UI 작업에는 스크린샷을 첨부한다.
- GitHub Actions 통과와 CodeRabbit 의견을 확인한 뒤 Squash merge하고 작업 브랜치를 삭제한다.
- 별도의 팀원 리뷰 승인 절차는 두지 않는다.

## CodeRabbit 운영

- PR 생성·업데이트 시 AI 리뷰로 잠재 오류와 예외 상황을 확인한다.
- Kotlin, Compose, Room 품질과 중복 코드 및 개선 제안을 검토한다.
- 의견은 타당하고 필요한 부분만 반영한다.
- CodeRabbit 승인은 병합 조건이 아니며 팀원 리뷰 승인 규칙도 두지 않는다.

## 라벨 운영

- Issue 제목의 `[Type]` 접두사로 Type 라벨을 자동 지정한다.
- PR은 브랜치 접두사로 Type 라벨, 변경 파일 경로로 Area 라벨을 자동 지정한다.
- 한 작업이 여러 영역을 변경하면 Area 라벨을 여러 개 지정할 수 있다.
- Member 라벨은 Issue 또는 PR 작성자의 GitHub 계정을 기준으로 자동 지정한다.
- 자동 관리 라벨이 변경되면 다음 Issue 편집 또는 PR 업데이트 때 기존 Type·Area·Member 라벨을 정리한다.
