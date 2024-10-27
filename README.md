# ProductDisplayApp
  하나의 api를 통해 BANNER, GRID, SCROLL, STYLE 타입의 콘텐츠 리스트 데이터를 받아 전시된 상품 리스트를 확인할 수 있다.

<br/>

   
## Tech
* 언어 : Kotlin
* 디자인 패턴 : MVI (Mavericks)
* 사용 라이브러리
  * api 통신 : Retrofit, OkHttp, Kotlin-serialization
  * 의존성 주입 : Hilt
  * 디자인 패턴 : Mavericks
  * 이미지 : Coil
  * Ui : Compose

  <br/>


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
  * state가 업데이트 되면 조건(LoadState)에 따라 LoadingScreen, ComponentScreen 또는 ErrorScreen 노출

<br/>

## 용어 정리
* Component = Header + Content + Footer  
* Content = List 형식으로 4가지 타입 존재 (BANNER, GRID, SCROLL, STYLE)

<br/>

## 컴포넌트 데이터 설명
```
  data class ComponentUiModel(
      val contentType: ContentType,
      val contentList: List<ContentUiModel>,
      val headerUiModel: HeaderUiModel,
      val footerUiModel: FooterUiModel  
  )
```
* contentType : 콘텐츠 타입 ex) BANNER, GRID, SCROLL, STYLE
* contentList : 콘텐츠 리스트. 각 타입별로 UiModel이 존재하여 ContentUiModel을 sealed interface로 만들어서 묶어줌
* headerUiModel : 컴포넌트의 콘텐츠 영역 위에 있는 헤더
* footerUiModel : 컴포넌트의 콘텐츠 영역 아래 있는 푸터

<br/>


## State
```
sealed interface LoadState {
    data object Loading : LoadState
    data class Success(val displayComponents: List<ComponentUiModel>) : LoadState
    data class Error(val error: Throwable): LoadState
}

data class ComponentUiState(
    val loadState: LoadState = LoadState.Loading
) : MavericksState
```
ComponentUiModel이 하나의 컴포넌트이고, 컴포넌트 리스트를 state에 정의


<br/>

## Event (footer 동작)
```
sealed interface ComponentEvent {
    data class Refresh(val type: ContentType): ComponentEvent
    data class LoadMore(val type: ContentType): ComponentEvent
}
```

<br/>

## 컴포넌트 설명
* BannerComponent
  * 코루틴을 사용하여 3초 간격으로 자동 스와이프 구현
  * pageCount = Int.MAX_VALUE로 설정 후 무한 롤링 구현 (사용하던 코틀린 버전에서 overflow 이슈가 있어서 코틀린 버전 up하여 해결)
  * BackgroundImagePager와 ForegroundTitlePager를 사용하여 parallax 배너 구현

* GoodsComponent
  * 상품 관련 컴포넌트로 GridComponent와 ScrollComponent이 있음
  * 상품 아이템인 GoodsItem를 재활용하여 Grid Type일 경우 GridComponent, Scroll Type일 경우 ScrollComponent 노출
  
* StyleComponent
  * 이미지로 구성된 스타일 아이템이 조합된 컴포넌트
  * 상품 아이템에서 사용하는 AsyncImageItem을 재활용

* HeaderComponent
  * 헤더 컴포넌트로 Title의 길이에 따라 Icon의 배치가 달라지고, Icon과 Link 버튼 유/무에 따라 Title의 maxWidth가 달라지므로 Layout으로 구현

* FooterComponent
  * REFRESH, MORE 타입 존재
  * 각 기능은 ViewModel에서 처리한 후 state 업데이트하여 Ui를 업데이트

<br/>

