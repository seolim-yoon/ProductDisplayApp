# ProductDisplayApp
  하나의 api를 통해 BANNER, GRID, SCROLL, STYLE 타입의 콘텐츠 리스트 데이터를 받아 전시된 상품 리스트를 확인할 수 있다.

## Tech
* 언어 : Kotlin
* 디자인 패턴 : MVI (Mavericks)
* 사용 라이브러리
  * api 통신 : Retrofit, OkHttp, Kotlin-serialization
  * 의존성 주입 : Hilt
  * 디자인 패턴 : Mavericks
  * 이미지 : Coil
  * Ui : Compose


## 프로젝트 구조
* Data
  * DTO 생성
  * api를 받아 Datasource로 전송
  * Datasource에서 api 받아 처리 또는 product_mock_response.json으로 테스트 가능
  * Repository에서 DTO -> Entity 맵핑
 
* Domain
  * Entity 생성
  * Usecase에서 컴포넌트 리스트 반환
 
* App
  * UiModel 생성
  * ViewModel에서 Usecase를 통해 컴포넌트 리스트 가져온 후, Entity -> UiModel 맵핑
  * UiModel로 변환된 컴포넌트 리스트를 state로 저장
  * state가 업데이트 되면 조건에 따라 ComponentScreen 또는 ErrorScreen 노출
 

## 컴포넌트 설명
