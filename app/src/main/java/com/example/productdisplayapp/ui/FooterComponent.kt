package com.example.productdisplayapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.domain.util.FooterType
import com.example.productdisplayapp.ui.theme.ProductDisplayAppTheme
import com.example.productdisplayapp.uimodel.FooterUiModel

@Composable
internal fun FooterComponent(
    footer: FooterUiModel,
    onFooterClick: (FooterType) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable {
                onFooterClick(footer.footerType)
            }
    ) {

        if (footer.footerType == FooterType.REFRESH) {
            if (LocalInspectionMode.current) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
            } else {
                AsyncImageItem(
                    url = footer.iconURL
                )
            }
        }

        Text(
            text = footer.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 5.dp)
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun PreviewFooterItem(@PreviewParameter(FooterItemParameterProvider::class) footer: FooterUiModel) {
    ProductDisplayAppTheme {
        FooterComponent(
            footer = footer,
            onFooterClick = {}
        )
    }
}

private class FooterItemParameterProvider(
    override val values: Sequence<FooterUiModel> = sequenceOf(
        refreshCase,
        moreCase
    )
): PreviewParameterProvider<FooterUiModel>

private val refreshCase = FooterUiModel(
    title = "새로운 추천",
    iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
    footerType = FooterType.REFRESH
)

private val moreCase = FooterUiModel(
    title = "더보기",
    iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
    footerType = FooterType.MORE
)