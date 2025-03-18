package com.example.productdisplayapp.ui

import androidx.compose.foundation.clickable
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
internal fun StyleComponent(
    styleList: List<StyleUiModel>,
    onContentClick:(String) -> Unit
) {

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        if (styleList.size > 2) {
            Row(
                modifier = Modifier.aspectRatio(1f)
            ) {
                AsyncImageItem(
                    url = styleList[0].thumbnailURL,
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            onContentClick(styleList[0].linkURL)
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImageItem(
                        url = styleList[1].thumbnailURL,
                        modifier = Modifier
                            .clickable {
                                onContentClick(styleList[1].linkURL)
                            }
                            .weight(1f),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    AsyncImageItem(
                        url = styleList[2].thumbnailURL,
                        modifier = Modifier
                            .clickable {
                                onContentClick(styleList[2].linkURL)
                            }
                            .weight(1f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMN_DEFAULT),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            userScrollEnabled = false,
            modifier = Modifier.heightIn(max = 2000.dp)
        ) {
            items(
                key = { style ->
                    style.id
                },
                items = styleList.drop(3)
            ) { style ->
                AsyncImageItem(
                    url = style.thumbnailURL,
                    modifier = Modifier.clickable {
                        onContentClick(style.linkURL)
                    }
                )
            }
        }
    }
}
