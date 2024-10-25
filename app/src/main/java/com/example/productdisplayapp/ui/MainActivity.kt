package com.example.productdisplayapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.domain.util.ContentType
import com.example.domain.util.FooterType
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
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ComponentScreen(
                        modifier = Modifier.padding(innerPadding)
                            .background(
                                color = Color.White
                            )
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

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect {
            when (it) {
                is ComponentUiEffect.ShowToastMsg -> {
                    Toast.makeText(context, it.errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(
            contentType = { component -> component.contentType },
            items = state.displayComponents
        ) { component ->

            ComponentItem(
                component = component,
                onContentClick = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                },
                onFooterClick = { type ->
                    when (type) {
                        FooterType.REFRESH -> {
                            viewModel.refreshComponentList(component.contentType)
                        }
                        FooterType.MORE -> {
                            viewModel.loadMoreComponentList(component.contentType)
                        }
                        FooterType.NONE -> {}
                    }
                }
            )
        }
    }
}


@Composable
fun ComponentItem(
    component: ComponentUiModel,
    onContentClick: (String) -> Unit,
    onFooterClick: (FooterType) -> Unit
) {
    if (component.headerUiModel.title.isNotEmpty()) {
        HeaderComponent(
            header = component.headerUiModel,
//            header = HeaderUiModel(
//                title = "클리어런스",
//                iconURL = "https://image.msscdn.net/icons/mobile/clock.png",
//                linkURL = ""
//            ),
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

    Spacer(modifier = Modifier.height(35.dp))
}
