# WATT IF Pleos 차량용 앱 개발 가이드

이 문서는 WATT IF에 필요한 Pleos 개발 원칙만 정리한다. 공식 문서에 API가 존재하더라도 특정 차량에서 조회·제어가 가능하다는 뜻은 아니다. 구현 전에 SDK 상태, 권한, 차량별 capability를 확인한다.

## 현재 적용 상태

- 앱은 Android API 34, minSdk 28의 Compose 프로젝트이며 Pleos Connect Emulator 2.1.0을 대상으로 한다.
- Vehicle SDK와 NaviHelper SDK의 의존성, 저장소, 권한, 실제 호출 코드는 아직 추가되지 않았다.
- 현재 Room 데이터와 화면은 Android 앱 내부 기능이며 차량 데이터 연동 결과가 아니다.

## 개발 환경

1. Android Studio와 JDK 17을 사용한다.
2. Android SDK API 34와 Pleos Connect Emulator 2.1.0 system image를 설치한다.
3. AVD는 공식 안내에 따라 Automotive 프로필, API 34, Cold boot, RAM 4 GB로 구성한다.
4. SDK 연동 전에 Pleos Playground 프로젝트와 SDK 사용 상태를 확인한다.
5. 사용할 API의 `@RequiresPermission`을 확인하고 필요한 권한만 Manifest에 선언한다.

공식 빠른 시작 문서는 minSdk 28 이상, API 34, Pleos Maven 저장소와 Vehicle SDK 의존성 설정을 안내한다. WATT IF에는 아직 SDK가 연동되지 않았으므로 버전이나 인증 정보를 임의로 추가하지 않는다.

## 차량용 UI

- 가로형 차량 디스플레이를 우선 고려하되 크기·방향 변화에도 내용이 잘리거나 겹치지 않게 한다.
- 배터리 잔량, 보호 기준과 경고는 짧고 명확하게 표시하며 색상에만 의존하지 않는다.
- 주요 화면은 Compose Preview에서 샘플 상태로 확인하고 실제 SDK를 호출하지 않는다.
- Emulator와 공식 폼 팩터 테스트 도구에서 텍스트 크기, 정렬, 터치, 화면 전환을 확인한다.
- 주행 중 운전자 주의 분산을 줄이고 앱 카테고리 및 지역별 규제에 따른 인터랙션 제한을 준수한다. 제한 여부를 임의로 판단하지 않는다.

## Vehicle SDK와 NaviHelper SDK

- Vehicle SDK는 필요한 차량 데이터 범위를 먼저 정하고 조회 API와 제어 API를 구분한다.
- NaviHelper SDK는 WATT IF에 필요한 목적지·경로 정보의 실제 제공 범위를 공식 API 문서에서 확인한 뒤 사용한다.
- 기능 호출 전에 capability와 접근 수준을 확인한다. 미지원 또는 권한 없음이면 UI를 비활성화하거나 대체 흐름을 제공한다.
- 비동기 조회의 성공과 실패를 모두 처리하고, 실패·null·범위 밖 값이 정상 화면을 깨뜨리지 않게 한다.
- SDK 데이터와 Mock 데이터는 타입 또는 데이터 소스 경계에서 구분하고 사용자·테스트 로그에서 식별 가능하게 한다.
- 차량 제어는 해당 차량의 지원 여부, WRITE 권한, 공식 정책과 사용자 동의가 확인된 기능만 구현한다.
- API 문서의 존재만으로 실제 차량 제어 가능성을 단정하지 않는다. 현재 공식 프로젝트 안내상 Vehicle State와 NaviHelper는 사용 가능 항목으로 설명되지만, Vehicle Control SDK는 준비 중으로 안내된다.

## 테스트 체크리스트

- [ ] 차량 데이터 조회 성공·실패·null·미지원 처리
- [ ] capability와 권한 부족 시 대체 UI
- [ ] 화면 이동 후 상태 유지와 앱 재실행 복원
- [ ] Room 데이터 저장·조회·복원 및 스키마 변경 테스트
- [ ] Compose Preview가 SDK 없이 렌더링되는지 확인
- [ ] Pleos Connect Emulator에서 화면과 API 실패 처리 확인
- [ ] 가로형 및 크기가 다른 디스플레이에서 잘림·겹침 확인
- [ ] 주행·정차 상태별 UI 제한 확인
- [ ] 실제 데이터와 Mock 데이터가 명확히 구분되는지 확인

## 공식 문서

- [Pleos Connect 앱 개발 시작하기](https://document.pleos.ai/docs/pleos-only/getting-started)
- [프로젝트 생성 및 SDK 사용 신청](https://document.pleos.ai/docs/pleos-only/create-my-project)
- [Pleos Connect Emulator 설치 및 실행](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator)
- [Pleos Connect 앱 설계 가이드](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/intro)
- [Vehicle SDK 권한](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/vehicle-permission)
- [Vehicle capability 확인](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/check-capability)
- [폼 팩터 테스트 가이드](https://document.pleos.ai/docs/pleos-only/app-test-guide/form-factor-test-guide)
- [현대자동차그룹 차량용 앱 개발 공모전](https://appdevchallenge.auto-hmg.io/)
