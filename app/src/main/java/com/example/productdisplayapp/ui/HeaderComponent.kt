package com.example.productdisplayapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.R
import com.example.productdisplayapp.uimodel.HeaderUiModel
import com.example.productdisplayapp.util.HeaderSlot

@Composable
fun HeaderComponent(
    header: HeaderUiModel,
    onHeaderClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SubcomposeLayout(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RectangleShape
            )
    ) { constraints ->
        val iconPlaceable = subcompose(HeaderSlot.ICON) {
            AsyncImageItem(
                url = header.iconURL,
                modifier = Modifier.padding(end = 15.dp)
            )
        }.first().measure(constraints)

        val linkPlaceable =
            subcompose(HeaderSlot.LINK) {
                Text(
                    text = stringResource(R.string.all),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clickable {
                            onHeaderClick(header.linkURL)
                        }
                )
        }.first().measure(constraints)

        val iconPlaceableWidth = if (header.iconURL.isNotEmpty()) { iconPlaceable.width } else 0
        val linkPlaceableWidth = if (header.linkURL.isNotEmpty()) { linkPlaceable.width } else 0
        val titleMaxWidth = constraints.maxWidth - iconPlaceableWidth - linkPlaceableWidth

        val titlePlaceable = subcompose(HeaderSlot.TITLE) {
            Text(
                text = header.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .widthIn(max = titleMaxWidth.toDp())
                    .padding(15.dp)
            )
        }.first().measure(constraints.copy(maxWidth = titleMaxWidth))

        val width = constraints.maxWidth
        val height = maxOf(
            titlePlaceable.height,
            iconPlaceable.height,
            linkPlaceable.height
        )

        layout(width, height) {
            titlePlaceable.placeRelative(
                x = 0,
                y = (height - titlePlaceable.height) / 2
            )
            if (header.iconURL.isNotEmpty()) {
                iconPlaceable.placeRelative(
                    x = titlePlaceable.width,
                    y = (height - iconPlaceable.height) / 2
                )
            }
            if (header.linkURL.isNotEmpty()) {
                linkPlaceable.placeRelative(
                    x = width - linkPlaceable.width,
                    y = (height - linkPlaceable.height) / 2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderItem() {
    HeaderComponent(
        HeaderUiModel(
            title = "클리어런스",
            iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
            linkURL = "https://www.musinsa.com/brands/discoveryexpedition?category3DepthCodes=&category2DepthCodes=&category1DepthCode=018&colorCodes=&startPrice=&endPrice=&exclusiveYn=&includeSoldOut=&saleGoods=&timeSale=&includeKeywords=&sortCode=discount_rate&tags=&page=1&size=90&listViewType=small&campaignCode=&groupSale=&outletGoods=false&boutiqueGoods="
        ),
        onHeaderClick = {}
    )
}