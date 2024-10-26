package com.example.productdisplayapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.R
import com.example.productdisplayapp.ui.theme.ProductDisplayAppTheme
import com.example.productdisplayapp.uimodel.HeaderUiModel

@Composable
internal fun HeaderComponent(
    header: HeaderUiModel,
    onHeaderClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RectangleShape
            )
            .padding(15.dp),
        content = {
            Box {
                Text(
                    text = header.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
            }

            Box {
                if (header.iconURL.isNotEmpty()) {
                    if (LocalInspectionMode.current) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    } else {
                        AsyncImageItem(
                            url = header.iconURL,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }

            Box {
                if (header.linkURL.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.all),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                onHeaderClick(header.linkURL)
                            }
                    )
                }
            }
        }
    ) { measureable, constraints ->

        val (titleMeasurable, iconMeasurable, linkMeasurable) = measureable

        val iconPlaceable = iconMeasurable.measure(
            Constraints(maxWidth = constraints.maxWidth)
        )
        val linkPlaceable = linkMeasurable.measure(
            Constraints(maxWidth = constraints.maxWidth - iconPlaceable.width)
        )
        val titlePlaceable = titleMeasurable.measure(
            Constraints(maxWidth = constraints.maxWidth - iconPlaceable.width - linkPlaceable.width)
        )

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
            iconPlaceable.placeRelative(
                x = titlePlaceable.width,
                y = (height - iconPlaceable.height) / 2
            )
            linkPlaceable.placeRelative(
                x = width - linkPlaceable.width,
                y = (height - linkPlaceable.height) / 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeaderItem(@PreviewParameter(HeaderItemParameterProvider::class) header: HeaderUiModel) {
    ProductDisplayAppTheme {
        HeaderComponent(
            header = header,
            onHeaderClick = {}
        )
    }
}

private class HeaderItemParameterProvider(
    override val values: Sequence<HeaderUiModel> = sequenceOf(
        fullCase,
        emptyIconCase,
        emptyLinkCase,
        longNameCase
    )
): PreviewParameterProvider<HeaderUiModel>

private val fullCase = HeaderUiModel(
    title = "클리어런스",
    iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
    linkURL = "https://www.musinsa.com/brands/discoveryexpedition?category3DepthCodes=&category2DepthCodes=&category1DepthCode=018&colorCodes=&startPrice=&endPrice=&exclusiveYn=&includeSoldOut=&saleGoods=&timeSale=&includeKeywords=&sortCode=discount_rate&tags=&page=1&size=90&listViewType=small&campaignCode=&groupSale=&outletGoods=false&boutiqueGoods="
)

private val emptyIconCase = HeaderUiModel(
    title = "클리어런스",
    iconURL = "",
    linkURL = "https://www.musinsa.com/brands/discoveryexpedition?category3DepthCodes=&category2DepthCodes=&category1DepthCode=018&colorCodes=&startPrice=&endPrice=&exclusiveYn=&includeSoldOut=&saleGoods=&timeSale=&includeKeywords=&sortCode=discount_rate&tags=&page=1&size=90&listViewType=small&campaignCode=&groupSale=&outletGoods=false&boutiqueGoods="
)

private val emptyLinkCase = HeaderUiModel(
    title = "클리어런스",
    iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
    linkURL = ""
)

private val longNameCase = HeaderUiModel(
    title = "클리어런스클리어런스클리어런스클리어런스클리어런스클리어런스클리어런스",
    iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
    linkURL = "https://www.musinsa.com/brands/discoveryexpedition?category3DepthCodes=&category2DepthCodes=&category1DepthCode=018&colorCodes=&startPrice=&endPrice=&exclusiveYn=&includeSoldOut=&saleGoods=&timeSale=&includeKeywords=&sortCode=discount_rate&tags=&page=1&size=90&listViewType=small&campaignCode=&groupSale=&outletGoods=false&boutiqueGoods="
)

