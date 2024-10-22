package com.example.productdisplayapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.productdisplayapp.uimodel.BannerUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerComponent(
    bannerList: List<BannerUiModel>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { bannerList.size })

    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            key = { bannerList[it].id },
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.aspectRatio(1f)
        ) { page ->
            bannerList.getOrNull(page)?.let { banner ->
                BannerItem(
                    banner = banner,
                )
            }
        }

        BannerIndicator(
            currentIdx = pagerState.currentPage + 1,
            totalCount = pagerState.pageCount,
            modifier =  Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun BannerItem(
    banner: BannerUiModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            rememberAsyncImagePainter(
                model = banner.thumbnailURL
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        BannerTitle(
            title = banner.title,
            description = banner.description,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BannerTitle(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = description,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun BannerIndicator(
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
fun PreviewBannerItem() {
    val banner = BannerUiModel(
        id = 1,
        linkURL = "https://www.musinsa.com/app/campaign/index/junebeautyfull",
        thumbnailURL = "https://image.msscdn.net/images/event_banner/2022062311154900000044053.jpg",
        title = "하이드아웃 S/S 시즌오프",
        description = "최대 30% 할인",
        keyword = "세일"
    )
    BannerItem(
        banner
    )
}
