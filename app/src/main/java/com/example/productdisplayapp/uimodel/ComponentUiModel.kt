package com.example.productdisplayapp.uimodel

import com.example.domain.util.ContentType

data class ComponentUiModel(
    val contentType: ContentType,
    val contentList: List<ContentUiModel>,
    val headerUiModel: HeaderUiModel,
    val footerUiModel: FooterUiModel  
)