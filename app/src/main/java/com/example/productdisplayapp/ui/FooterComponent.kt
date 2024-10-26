package com.example.productdisplayapp.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.util.FooterType
import com.example.productdisplayapp.uimodel.FooterUiModel

@Composable
fun FooterComponent(
    footer: FooterUiModel,
    onFooterClick: (FooterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable {
                Log.e("seolim", "click")
                onFooterClick(footer.footerType)
            }
    ) {

        if (footer.footerType == FooterType.REFRESH) {
            AsyncImageItem(
                url = footer.iconURL
            )
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
fun PreviewFooterItem() {
    FooterComponent(
        footer = FooterUiModel(
            title = "새로운 추천",
            iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
            footerType = FooterType.REFRESH
        ),
        onFooterClick = {}
    )
}