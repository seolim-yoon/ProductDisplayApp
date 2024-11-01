package com.example.productdisplayapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.util.ContentType
import com.example.domain.util.FooterType
import com.example.productdisplayapp.uimodel.BannerUiModel
import com.example.productdisplayapp.uimodel.ComponentUiModel
import com.example.productdisplayapp.uimodel.GoodsUiModel
import com.example.productdisplayapp.uimodel.StyleUiModel

@Composable
internal fun ComponentScreen(
    state: ComponentUiState,
    onEvent: (ComponentEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.loadState) {
        is LoadState.Loading -> {
            LoadingScreen()
        }

        is LoadState.Success -> {
            ComponentListScreen(
                componentList = state.loadState.displayComponents,
                onEvent = onEvent,
                modifier = modifier
            )
        }

        is LoadState.Error -> {
            ErrorScreen(
                errorMessage = state.loadState.error.message.toString()
            )
        }
    }
}

@Composable
internal fun ComponentListScreen(
    componentList: List<ComponentUiModel>,
    onEvent: (ComponentEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(bottom = 35.dp),
        modifier = modifier
    ) {
        items(
            contentType = { component -> component.contentType },
            items = componentList
        ) { component ->

            ComponentItem(
                component = component,
                onContentClick = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                },
                onFooterClick = { type ->
                    when (type) {
                        FooterType.REFRESH -> {
                            onEvent(ComponentEvent.Refresh(component.contentType))
                        }
                        FooterType.MORE -> {
                            onEvent(ComponentEvent.LoadMore(component.contentType))
                        }
                        FooterType.NONE -> {}
                    }
                }
            )
        }
    }
}

@Composable
internal fun ComponentItem(
    component: ComponentUiModel,
    onContentClick: (String) -> Unit,
    onFooterClick: (FooterType) -> Unit
) {
    if (component.headerUiModel.title.isNotEmpty()) {
        HeaderComponent(
            header = component.headerUiModel,
            onHeaderClick = onContentClick
        )
    }

    when (component.contentType) {
        ContentType.BANNER -> {
            BannerComponent(
                bannerList = component.contentList.filterIsInstance<BannerUiModel>(),
                onContentClick = onContentClick
            )
        }

        ContentType.GRID -> {
            GridComponent(
                goodsList = component.contentList.filterIsInstance<GoodsUiModel>(),
                onContentClick = onContentClick
            )
        }

        ContentType.SCROLL -> {
            ScrollComponent(
                goodsList = component.contentList.filterIsInstance<GoodsUiModel>(),
                onContentClick = onContentClick
            )
        }

        ContentType.STYLE -> {
            StyleComponent(
                styleList = component.contentList.filterIsInstance<StyleUiModel>(),
                onContentClick = onContentClick
            )
        }

        else -> {}
    }

    if (component.footerUiModel.footerType != FooterType.NONE) {
        FooterComponent(
            component.footerUiModel,
            onFooterClick = onFooterClick
        )
    }
}

@Composable
internal fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                color = Color.White
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun ErrorScreen(
    errorMessage: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingScreen() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorScreen() {
    ErrorScreen(errorMessage = "에러 발생!")
}