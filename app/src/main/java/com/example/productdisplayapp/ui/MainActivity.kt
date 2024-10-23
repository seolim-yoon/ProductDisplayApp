package com.example.productdisplayapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.domain.util.ContentType
import com.example.productdisplayapp.ui.theme.ProductDisplayAppTheme
import com.example.productdisplayapp.uimodel.BannerUiModel
import com.example.productdisplayapp.uimodel.ComponentUiModel
import com.example.productdisplayapp.uimodel.GoodsUiModel
import com.example.productdisplayapp.uimodel.StyleUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductDisplayAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComponentScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentScreen(
    viewModel: MainViewModel = mavericksActivityViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
    ) {
        items(
            contentType = { component -> component.contentType },
            items = state.components
        ) { component ->
            ComponentItem(
                component = component,
                onContentClick = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            )
        }
    }
}

@Composable
fun ComponentItem(
    component: ComponentUiModel,
    onContentClick: (String) -> Unit
) {
    when (component.contentType) {
        ContentType.BANNER ->
            BannerComponent(
                bannerList = component.contentList.filterIsInstance<BannerUiModel>(),
                onContentClick = onContentClick
            )

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
}

