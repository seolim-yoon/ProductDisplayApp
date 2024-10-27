@file:OptIn(ExperimentalFoundationApi::class)

package com.example.productdisplayapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.ui.theme.ProductDisplayAppTheme
import com.example.productdisplayapp.uimodel.BannerUiModel
import com.example.productdisplayapp.util.BANNER_AUTO_SWIPE
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.abs

@Composable
internal fun BannerComponent(
    bannerList: List<BannerUiModel>,
    onContentClick:(String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pageCount = Int.MAX_VALUE
    val backgroundImagePagerState = rememberPagerState(pageCount = { pageCount })
    val foregroundTitlePagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(key1 = foregroundTitlePagerState.currentPage) {
        while (true) {
            delay(BANNER_AUTO_SWIPE)
            if (abs(foregroundTitlePagerState.currentPageOffsetFraction) > 0.0f)
                continue
            withContext(NonCancellable) {
                foregroundTitlePagerState.animateScrollToPage(
                    page = (foregroundTitlePagerState.currentPage + 1)
                )
                backgroundImagePagerState.animateScrollToPage(
                    page = (foregroundTitlePagerState.currentPage)
                )
            }
        }
    }

    LaunchedEffect(
        key1 = foregroundTitlePagerState.currentPage,
        key2 = foregroundTitlePagerState.currentPageOffsetFraction
    ) {
        withContext(NonCancellable) {
            backgroundImagePagerState.animateScrollToPage(
                foregroundTitlePagerState.currentPage,
                foregroundTitlePagerState.currentPageOffsetFraction
            )
        }
    }

    Box(
        modifier = modifier
    ) {
        BackgroundImagePager(
            bannerList = bannerList,
            backgroundImagePagerState = backgroundImagePagerState
        )
        ForegroundTitlePager(
            bannerList = bannerList,
            foregroundTitlePagerState = foregroundTitlePagerState,
            onContentClick = onContentClick
        )
        BannerIndicator(
            currentIdx = (backgroundImagePagerState.currentPage % bannerList.size) + 1,
            totalCount = bannerList.size,
            modifier =  Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
internal fun BackgroundImagePager(
    bannerList: List<BannerUiModel>,
    backgroundImagePagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        key = { bannerList[it % bannerList.size].id },
        state = backgroundImagePagerState,
        beyondViewportPageCount = 1,
        modifier = modifier,
    ) { page ->
        val parallaxFactor = 0.3f

        AsyncImageItem(
            url = bannerList[page % bannerList.size].thumbnailURL,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationX = abs(backgroundImagePagerState.currentPageOffsetFraction) * size.width * parallaxFactor
                }
        )
    }
}

@Composable
internal fun ForegroundTitlePager(
    bannerList: List<BannerUiModel>,
    foregroundTitlePagerState: PagerState,
    onContentClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        key = { bannerList[it % bannerList.size].id },
        state = foregroundTitlePagerState,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.aspectRatio(1f)
    ) { page ->
        val parallaxFactor = 0.5f
        val itemIdx = page % bannerList.size

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = abs(foregroundTitlePagerState.currentPageOffsetFraction) * size.width * parallaxFactor
                }
                .clickable {
                    onContentClick(bannerList[itemIdx].linkURL)
                }
        ) {
            BannerTitle(
                title = bannerList[itemIdx].title,
                description = bannerList[itemIdx].description,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
internal fun BannerTitle(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = description,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
internal fun BannerIndicator(
    currentIdx: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$currentIdx / $totalCount",
        color = Color.White,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .background(
                color = Color.DarkGray
            )
            .padding(vertical = 5.dp, horizontal = 15.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewBannerItem() {
    val banner = BannerUiModel(
        id = 1,
        linkURL = "https://www.musinsa.com/app/campaign/index/junebeautyfull",
        thumbnailURL = "https://image.msscdn.net/images/event_banner/2022062311154900000044053.jpg",
        title = "하이드아웃 S/S 시즌오프",
        description = "최대 30% 할인",
        keyword = "세일"
    )

    ProductDisplayAppTheme {
        BannerComponent(
            bannerList = listOf(banner),
            onContentClick = { },
        )
    }
}
