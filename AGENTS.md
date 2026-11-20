# WATT IF 에이전트 작업 지침

이 저장소에서 작업하는 에이전트는 작업을 시작하기 전에 아래 문서를 확인하고 준수한다.

- [개발 및 협업 컨벤션](docs/CONVENTION.md)
- [초기 아키텍처](docs/ARCHITECTURE.md)
- [Pleos 차량용 앱 개발 가이드](docs/PLEOS_GUIDE.md)

## 필수 원칙

- 기존 단일 `app` 모듈과 `com.breathofcharge.wattif` 패키지명을 유지한다.
- Kotlin과 Jetpack Compose 코드는 프로젝트 컨벤션을 따른다.
- 새로운 라이브러리, 모듈 또는 아키텍처를 임의로 도입하지 않는다.
- 변경 범위는 관련 Issue에 정의된 작업으로 제한한다.
- 공통 UI와 디자인 값은 기존 컴포넌트 및 디자인 토큰을 우선 사용한다.
- 주요 화면과 재사용 가능한 Compose 컴포넌트에는 `@Preview`를 작성한다.
- 작업 후 변경 범위에 맞는 테스트와 정적 분석을 실행한다.
- 차량 SDK 기능은 공식 API 지원 범위, 권한 및 차량 capability를 확인한 뒤 구현한다.
