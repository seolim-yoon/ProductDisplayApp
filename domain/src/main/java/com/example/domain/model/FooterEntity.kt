package com.example.domain.model

import com.example.domain.util.FooterType

data class FooterEntity(
    val title: String,
    val iconURL: String,
    val footerType: FooterType
)
