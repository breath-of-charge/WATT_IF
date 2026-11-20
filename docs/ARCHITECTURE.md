# WATT IF 초기 아키텍처

이 문서는 공모전 기획서의 기능을 구현 단계에서 어디에 배치할지 정한 초기 기준이다. 현재 커밋은 빌드 가능한 환경과 Room 활성 캠핑 스키마, Flow·StateFlow 기반 상태 관리를 제공한다. 팀 백엔드 API, 차량 제어와 배터리 계산 로직은 아직 연동되지 않았다.

## 계획된 경계

- `data/local`: Room(SQLite) 기반 캠핑 세션, 프리셋, 보호 설정, 체크리스트, 모드 사용 구간 저장
- `data/vehicle`: Pleos Vehicle SDK의 EvBattery, HVAC, Light, Door, Window 접근을 앱 모델로 변환
- `data/navigation`: NaviHelper SDK의 목적지·경로·예상 이동거리 접근
- `data/remote/api`: 팀 백엔드와 외부 서비스의 엔드포인트 정의
- `data/remote/dto`: 백엔드 요청·응답 모델
- `data/remote/datasource`: 네트워크 호출, 응답 검사와 오류 처리
- `domain`: 배터리 예산, 보호 기준, 캠핑 시작·종료 및 통계 규칙
- `ui`: 시작, 프리셋, 야간 대시보드, 종료 점검, 기록·리포트 화면과 화면별 ViewModel

현재 저장소는 단일 `app` 모듈을 사용한다. 화면은 `ui/<screen>` 패키지에 두며 별도의 feature 모듈이나 패키지는 사용하지 않는다. 화면은 domain 모델·Repository 인터페이스를 통해 데이터를 요청하고, `data/repository` 구현체가 백엔드 API·Room·차량·내비게이션 데이터 소스와 domain 사이를 연결한다. `MainActivity`는 앱 진입점으로서 구현체를 구성해 UI에 전달한다.

화면별 데이터 흐름은 다음과 같다.

```text
Screen → ViewModel → domain/repository → data/repository
                                            ├── remote datasource → Backend API
                                            ├── Room DAO
                                            ├── Vehicle SDK
                                            └── NaviHelper SDK
```

- Screen은 화면을 그리고 사용자 이벤트를 ViewModel에 전달한다.
- ViewModel은 `StateFlow`로 UiState를 관리하고 Repository만 호출한다.
- Repository는 데이터 출처를 선택하거나 결합하고 data 모델을 domain 모델로 변환한다.
- `remote/api`, DTO, DataSource는 UI와 ViewModel에서 직접 참조하지 않는다.

허용하는 의존성 방향은 다음과 같다.

```text
MainActivity → ui, data
ui → domain
data → domain
domain → (프로젝트 내부 계층 의존성 없음)
```

`ui`와 `domain`은 `data` 구현 세부사항에 직접 의존하지 않는다. 백엔드 API와 Room DAO는 data 구현 내부에서만 사용하며, ViewModel과 Composable에서 직접 참조하지 않는다. 패키지 배치 및 이 의존성 방향은 `ArchitectureKonsistTest`가 검증하고 Android CI에서 실행한다.

## 데이터 방향

캠핑 시작 시 시작 시간, 시작 배터리, 기존 차량 설정과 사용자 보호 기준을 저장한다. 진행 중인 세션은 Room에서 한 건의 활성 상태로 조회해 앱 재실행 시 복구한다. 종료 시 종료 시간과 종료 배터리, 모드별 사용 구간, 설정 복원 및 점검 상태를 기록하고, 저장된 이력을 집계해 총 이용 시간·횟수·배터리 사용량·가장 많이 사용한 모드를 제공한다.

팀 백엔드와 주고받을 데이터의 범위, 인증 방식과 동기화 기준은 API 명세가 확정된 뒤 결정한다. 명세 확정 전에는 Room 데이터와 백엔드 데이터를 임의로 중복 저장하거나 동기화하지 않는다.

## 배터리 예산 경계

목적지 기반 배터리 예산은 NaviHelper의 경로 정보와 Vehicle SDK의 배터리·전비 정보를 입력으로 받는 도메인 규칙으로 둔다. SDK에서 실제 제공하는 값과 차량별 권한을 확인하기 전에는 임의 계산식을 확정하지 않는다. 사용자가 지정한 최소 잔량은 별도 입력으로 유지하며, 기준 도달 시 기본 동작은 알림이고 자동 프리셋 해제는 지원 차량·권한·사전 동의가 모두 확인된 경우에만 활성화한다.

## 다음 구현 순서

1. 팀 백엔드 API 명세와 인증·오류 응답 형식 확정
2. 필요한 백엔드 클라이언트와 `data/remote` 경계 구현
3. Pleos 개발자 콘솔 프로젝트와 Vehicle SDK, NaviHelper SDK 의존성 연결
4. 캠핑 이력·프리셋·체크리스트 Room 스키마와 마이그레이션 정책 확장
5. SDK 접근용 인터페이스와 테스트 대역 정의
6. 캠핑 세션 상태 머신과 배터리 예산 규칙 구현
7. Compose 화면과 ViewModel 연결

구현 계획은 현재 코드 상태를 설명하는 문서가 아니다. Pleos SDK 연동과 추가 기능은 공식 API 지원 범위·권한·차량 capability를 확인한 뒤 진행한다.
