
package com.example.productdisplayapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
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
    onContentClick:(String) -> Unit
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(key1 = pagerState.currentPage) {
        while (true) {
            delay(BANNER_AUTO_SWIPE)
            withContext(NonCancellable) {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1
                )
            }
        }
    }

    Box {
        HorizontalPager(
            key = { bannerList[it % bannerList.size].id },
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            val parallaxFactor = 0.5f
            val itemIndex = page % bannerList.size

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onContentClick(bannerList[itemIndex].linkURL)
                    }
            ) {
                AsyncImageItem(
                    url = bannerList[itemIndex].thumbnailURL,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                BannerTitle(
                    title = bannerList[itemIndex].title,
                    description = bannerList[itemIndex].description,
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .graphicsLayer {
                            translationX = abs(pagerState.currentPageOffsetFraction) * size.width * parallaxFactor
                        }
                )
            }
        }

        BannerIndicator(
            currentIdx = (pagerState.currentPage % bannerList.size) + 1,
            totalCount = bannerList.size,
            modifier =  Modifier.align(Alignment.BottomEnd)
        )
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
        verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Bottom)
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
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
