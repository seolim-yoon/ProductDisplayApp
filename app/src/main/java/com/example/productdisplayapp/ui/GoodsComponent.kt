package com.example.productdisplayapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.R
import com.example.productdisplayapp.ui.theme.ProductDisplayAppTheme
import com.example.productdisplayapp.uimodel.GoodsUiModel
import com.example.productdisplayapp.util.GRID_COLUMN_DEFAULT

@Composable
internal fun GridComponent(
    goodsList: List<GoodsUiModel>,
    onContentClick:(String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_COLUMN_DEFAULT),
        contentPadding = PaddingValues(15.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.heightIn(max = 1000.dp)
    ) {
        items(
            key = { goods ->
                goods.id
            },
            items = goodsList
        ) { goods ->
            GoodsItem(
                goods = goods,
                onGoodsClick = {
                    onContentClick(goods.linkURL)
                }
            )
        }
    }
}

@Composable
internal fun ScrollComponent(
    goodsList: List<GoodsUiModel>,
    onContentClick:(String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = remember { LazyListState() }

    LaunchedEffect(key1 = goodsList) {
        listState.scrollToItem(0)
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(15.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            key = { goods ->
                goods.id
            },
            items = goodsList,
        ) { goods ->
            GoodsItem(
                goods = goods,
                onGoodsClick = {
                    onContentClick(goods.linkURL)
                },
                modifier = Modifier.fillParentMaxWidth(1f / 2.7f)
            )
        }
    }
}

@Composable
internal fun GoodsItem(
    goods: GoodsUiModel,
    onGoodsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable {
            onGoodsClick()
        }
    ) {
        GoodsImage(
            goods = goods
        )
        GoodsInfo(
            goods = goods
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
internal fun GoodsImage(
    goods: GoodsUiModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (LocalInspectionMode.current) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().size(150.dp)
            )
        } else {
            AsyncImageItem(
                url = goods.thumbnailURL,
                modifier = Modifier.fillMaxWidth()
            )
        }


        if (goods.hasCoupon) {
            Text(
                text = "쿠폰",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.blue)
                    )
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 5.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
internal fun GoodsInfo(
    goods: GoodsUiModel
) {
    Text(
        text = goods.brandName,
        color = Color.DarkGray,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 8.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.price_won, goods.price),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = stringResource(R.string.sale_rate, goods.saleRate),
            color = colorResource(R.color.dark_red),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 5.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 150)
@Composable
private fun PreviewGoodsItem(@PreviewParameter(GoodsItemParameterProvider::class) goods: GoodsUiModel) {
    ProductDisplayAppTheme {
        GoodsItem(
            goods = goods,
            onGoodsClick = {}
        )
    }
}

private class GoodsItemParameterProvider(
    override val values: Sequence<GoodsUiModel> = sequenceOf(
        couponCase,
        noCouponCase,
        longNameCase
    )
): PreviewParameterProvider<GoodsUiModel>

private val couponCase = GoodsUiModel(
    id = 0,
    linkURL = "https://www.musinsa.com/app/goods/2281818",
    thumbnailURL = "https://image.msscdn.net/images/goods_img/20211224/2281818/2281818_1_320.jpg",
    brandName = "아스트랄 프로젝션",
    price = 39900,
    saleRate = 50,
    hasCoupon = true
)

private val noCouponCase = GoodsUiModel(
    id = 0,
    linkURL = "https://www.musinsa.com/app/goods/2281818",
    thumbnailURL = "https://image.msscdn.net/images/goods_img/20211224/2281818/2281818_1_320.jpg",
    brandName = "아스트랄 프로젝션",
    price = 39900,
    saleRate = 50,
    hasCoupon = false
)

private val longNameCase = GoodsUiModel(
    id = 0,
    linkURL = "https://www.musinsa.com/app/goods/2281818",
    thumbnailURL = "https://image.msscdn.net/images/goods_img/20211224/2281818/2281818_1_320.jpg",
    brandName = "아스트랄 프로젝션12345656788",
    price = 39900,
    saleRate = 50,
    hasCoupon = false
)