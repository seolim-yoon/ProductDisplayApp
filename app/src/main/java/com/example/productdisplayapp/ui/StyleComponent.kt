package com.example.productdisplayapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.uimodel.StyleUiModel
import com.example.productdisplayapp.util.GRID_COLUMN_DEFAULT

@Composable
fun StyleComponent(
    styleList: List<StyleUiModel>,
    onContentClick:(String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(15.dp)
    ) {
        Row(
            modifier = Modifier.aspectRatio(1f)
        ){
            ContentImageItem(
                content = styleList[0],
                onImageClick = { onContentClick(styleList[0].linkURL) },
                modifier = Modifier.weight(2f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ContentImageItem(
                    content = styleList[1],
                    onImageClick = { onContentClick(styleList[1].linkURL) }
                )
                Spacer(modifier = Modifier.height(4.dp))
                ContentImageItem(
                    content = styleList[2],
                    onImageClick = { onContentClick(styleList[2].linkURL) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMN_DEFAULT),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.heightIn(max = 1000.dp)
        ) {
            items(
                key = { style ->
                    style.id
                },
                items = styleList.drop(3)
            ) { style ->
                ContentImageItem(
                    content = style,
                    onImageClick = { onContentClick(style.linkURL) }
                )
            }
        }
    }
}
