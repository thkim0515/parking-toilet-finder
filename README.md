# 공영지도 — 공영주차장·공중화장실 위치 안내 앱

> 서울·경기 지역의 공영주차장과 공중화장실을 지도에서 찾아주는 안드로이드 앱. 빈자리 정보가 있는 주차장은 여유/혼잡 상태를, 없는 곳은 위치만 표시한다.

---

## 목차

- [버전 관리 규칙](#버전-관리-규칙)
- [최신 업데이트](#최신-업데이트-2026-09-21--초기-버전-v010)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [데이터 모델](#데이터-모델)
- [화면 구성](#화면-구성)
- [네이버 지도 길 안내 연동](#네이버-지도-길-안내-연동)
- [디자인 출처](#디자인-출처)
- [추후 채워야 할 항목 (TODO)](#추후-채워야-할-항목-todo)
- [로컬 빌드 방법](#로컬-빌드-방법)

---

## 버전 관리 규칙

앱 버전은 `A.B.C` 형식(시맨틱 버저닝)을 따른다.

| 자리 | 의미 | 올리는 시점 |
|------|------|-------------|
| **A** (Major) | 큰 변화 | UI 전면 개편, 핵심 기능 추가·제거, 호환성 깨지는 변경 |
| **B** (Minor) | 작은 기능 추가 | 새 화면·새 기능·새 설정 항목 추가 등 사용자가 체감하는 개선 |
| **C** (Patch) | 버그 수정 | 오류 수정, 텍스트·스타일 미세 조정, 성능 개선 |

**업데이트 방법**: `app/build.gradle.kts` 의 `defaultConfig.versionName` 값을 수정한다.

**현재 버전**: v0.1.0 (2026-09-21 — 초기 버전, Mock 데이터 기반 4개 화면)

---

## 최신 업데이트 (2026-09-21 · 초기 버전 v0.1.0)

- 🆕 **프로젝트 초기 세팅** — Kotlin + Jetpack Compose, MVVM(ViewModel/Repository/UseCase 계층 분리), Hilt DI, Navigation Compose, Retrofit+Moshi 기반 Gradle Kotlin DSL 프로젝트를 처음부터 구성했습니다.
- 🆕 **claude.ai/design 디자인 이식** — "공영지도" 디자인 프로젝트(Industry 디자인 시스템)의 컬러·타이포·반경(radius) 토큰을 그대로 읽어와 `ui/theme` 아래 Compose 테마로 옮겼습니다. 버튼/입력/카드 radius, 여유(초록)/혼잡(주황~빨강)/정보없음(회색) 상태 색상, 화장실 마커 색상까지 디자인 원본 값과 동일합니다.
- 🆕 **지도 메인 화면** — 상단 검색바(placeholder) + 전체/주차장/화장실 세그먼트 필터, 지도 SDK 자리에 들어가는 placeholder Composable(`MapCanvasPlaceholder`) 위에 상태별 마커 표시, "현재 위치로 이동" FAB, "근처 목록 보기" 토글 바를 구현했습니다. 지도 SDK는 인터페이스 뒤에 감춰져 있어 나중에 네이버 지도 SDK로 교체해도 상위 화면 코드는 그대로 재사용됩니다.
- 🆕 **상세 정보 바텀시트** — 주차장은 이름/주소/총면수/빈자리(또는 "실시간 정보 미제공")/요금/운영시간을, 화장실은 이름/주소/개방시간/장애인 화장실 여부를 보여주는 `ModalBottomSheet`를 구현했습니다. 하단 "길 안내 시작" 버튼에 도보/차량 예상 소요시간을 함께 표시합니다.
- 🆕 **리스트 뷰** — 근처 장소를 거리순으로 정렬한 카드 리스트(`PlaceListContent`)를 구현했습니다. 카드마다 상태 아이콘, 이름, 거리, 상태 배지, "안내" 버튼이 있고 지도 화면과 토글로 전환됩니다.
- 🆕 **네이버 지도 딥링크 길 안내** — 이 앱은 자체 내비게이션 UI를 구현하지 않고, "길 안내 시작"을 누르면 `nmap://route/walk?...` 딥링크로 네이버 지도 앱의 길찾기 화면을 바로 실행합니다. 네이버 지도 앱이 없으면 플레이스토어 설치 페이지로 자동 이동하는 fallback도 포함했습니다(`ui/common/NaverMapDeepLink.kt`).
- 🆕 **Mock 데이터 계층** — `PlaceRepository` 인터페이스와 `MockPlaceRepository` 구현체로 지금은 더미 데이터(서울 중구 회현·명동 일대 샘플)가 화면에 표시됩니다. 공공데이터 API 키가 준비되면 Hilt 바인딩만 교체하면 되도록 설계했습니다.
- 🔧 **API 키/지도 SDK 자리만 미리 마련** — 네이버 지도 SDK 클라이언트 ID, 공공데이터포털 API 키는 `local.properties`(git 미포함)에 빈 값으로 두고, `BuildConfig` 필드로만 노출해뒀습니다. 값이 채워지면 코드 변경 없이 바로 반영됩니다.
- ⚠️ **디버그 서명 APK로 최초 릴리즈** — 정식 keystore가 아직 없어 `v0.1.0` GitHub Release는 디버그 서명 APK로 배포됩니다. 정식 배포 전 재서명이 필요합니다.

---

## 기술 스택

| 구분 | 사용 기술 |
|---|---|
| 언어 | Kotlin |
| UI | Jetpack Compose (Material 3) |
| 아키텍처 | MVVM (ViewModel / Repository / UseCase) |
| 비동기 | Kotlin Coroutines + Flow |
| DI | Hilt |
| 네트워킹 | Retrofit + Moshi (인터페이스만 정의, 실제 baseUrl/키는 추후 연동) |
| 화면 전환 | Navigation Compose |
| 지도 | (예정) 네이버 지도 Android SDK — 현재는 placeholder Composable + 인터페이스만 |
| 길 안내 | 네이버 지도 앱 딥링크(`nmap://route/...`), 자체 내비게이션 미구현 |

## 프로젝트 구조

```
app/src/main/java/com/parkingtoiletfinder/app/
├── ParkingToiletApp.kt          # @HiltAndroidApp Application
├── MainActivity.kt              # Compose 진입점
├── data/
│   ├── model/                   # Place(sealed class: Parking/Restroom), LatLng
│   ├── remote/                  # PublicDataApiService, DTO (TODO: 실제 스키마 미연동)
│   └── repository/              # PlaceRepository 인터페이스, MockPlaceRepository
├── domain/usecase/              # GetNearbyPlacesUseCase, GetPlaceDetailUseCase
├── di/                          # NetworkModule, RepositoryModule (Hilt)
└── ui/
    ├── theme/                   # Color/Type/Shape/Theme — 디자인 토큰 이식
    ├── navigation/               # NavGraph
    ├── map/                      # MapScreen, MapViewModel, MapCanvasPlaceholder 등
    ├── detail/                   # PlaceDetailBottomSheet
    ├── list/                     # PlaceListContent, PlaceCard
    └── common/                   # NaverMapDeepLink
```

## 데이터 모델

`Place` sealed class 하나로 주차장/화장실을 공통 표현한다.

```kotlin
sealed class Place {
    data class Parking(..., val totalSpots: Int, val availableSpots: Int?, ...) : Place()
    data class Restroom(..., val hasAccessibleToilet: Boolean) : Place()
}
```

- `availableSpots == null` → 실시간 정보 미제공(회색 마커)
- 빈자리 비율 15% 미만 → 혼잡(빨강), 그 이상 → 여유(초록)

## 화면 구성

1. **지도 메인 화면** — 검색바, 필터, 지도(placeholder), 마커, 현재 위치 FAB, 목록 보기 토글
2. **마커 상세 정보 바텀시트** — 마커/카드 탭 시 표시, "길 안내 시작" 버튼
3. **리스트 뷰** — 거리순 카드 리스트, 지도 화면과 토글로 전환

## 네이버 지도 길 안내 연동

`ui/common/NaverMapDeepLink.kt` 의 `launchNaverMapDirections()` 가 `nmap://route/walk?dlat=...&dlng=...&dname=...&appname=<패키지명>` 딥링크로 네이버 지도 앱을 실행한다. 안드로이드 11+ 패키지 가시성 정책 때문에 `AndroidManifest.xml` 의 `<queries>` 에 `com.nhn.android.nmap` 을 선언해뒀다. 앱이 없으면 플레이스토어 설치 페이지로 이동한다.

## 디자인 출처

claude.ai/design 의 "공영지도" 프로젝트(Industry 디자인 시스템 기반)를 `DesignSync` MCP로 읽어와 컬러·타이포·반경 토큰과 화면 레이아웃/상태 표현 방식을 Compose로 옮겼다. 원본 웹폰트(Barlow / Barlow Condensed)는 아직 실제 폰트 파일을 번들링하지 않고 시스템 SansSerif로 대체돼 있다(아래 TODO 참고).

## 추후 채워야 할 항목 (TODO)

- [ ] 네이버 지도 Android SDK 클라이언트 ID 발급 후 `local.properties` 의 `NAVER_MAP_CLIENT_ID` 채우고, `MapCanvasPlaceholder` 를 실제 네이버 지도 SDK 연동 Composable로 교체
- [ ] 공공데이터포털 "주차장정보" / "전국공중화장실표준데이터" API 인증키 발급 후 `PUBLIC_DATA_API_KEY` 채우고, `RemotePlaceRepository` 구현체 추가 + `di/RepositoryModule` 바인딩 교체
- [ ] `data/remote/PublicDataApiService.kt`, `Dto.kt` 를 실제 API 응답 스키마에 맞게 수정
- [ ] Barlow / Barlow Condensed 실제 폰트 파일을 `res/font` 에 추가해 디자인과 완전히 동일한 타이포그래피로 교체
- [ ] 실제 GPS 현재 위치 연동 (`FusedLocationProviderClient`), 런타임 위치 권한 요청 플로우
- [ ] 정식 릴리즈 keystore 발급 및 서명 설정 (현재 v0.1.0 은 디버그 서명 APK)

## 로컬 빌드 방법

```bash
git clone https://github.com/thkim0515/parking-toilet-finder.git
cd parking-toilet-finder
cp local.properties.example local.properties
# local.properties 의 sdk.dir 을 본인 Android SDK 경로로 수정
./gradlew assembleDebug
```

빌드된 APK는 `app/build/outputs/apk/debug/app-debug.apk` 에 생성된다.
