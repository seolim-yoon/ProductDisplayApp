package com.example.domain.model

sealed class ContentEntity

data class BannerEntity(
    val linkURL: String,
    val thumbnailURL: String,
    val title: String,
    val description: String,
    val keyword: String
): ContentEntity()

data class GoodsEntity(
    val linkURL: String,
    val thumbnailURL: String,
    val brandName: String,
    val price: Int,
    val saleRate: Int,
    val hasCoupon: Boolean
): ContentEntity()

data class StyleEntity(
    val linkURL: String,
    val thumbnailURL: String
): ContentEntity()

