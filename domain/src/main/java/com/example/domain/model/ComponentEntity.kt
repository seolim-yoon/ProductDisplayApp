package com.example.domain.model

import com.example.domain.util.ContentType

data class ComponentEntity(
    val contentType: ContentType,
    val contentList: List<ContentEntity>,
    val headerEntity: HeaderEntity,
    val footerEntity: FooterEntity
)