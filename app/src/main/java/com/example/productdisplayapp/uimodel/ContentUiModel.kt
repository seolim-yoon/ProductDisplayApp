package com.example.productdisplayapp.uimodel

sealed interface ContentUiModel {
    val id: Int
    val linkURL: String
    val thumbnailURL: String
}

data class BannerUiModel(
    override val id: Int,
    override val linkURL: String,
    override val thumbnailURL: String,
    val title: String,
    val description: String,
    val keyword: String
): ContentUiModel

data class GoodsUiModel(
    override val id: Int,
    override val linkURL: String,
    override val thumbnailURL: String,
    val brandName: String,
    val price: Int,
    val saleRate: Int,
    val hasCoupon: Boolean
): ContentUiModel

data class StyleUiModel(
    override val id: Int,
    override val linkURL: String,
    override val thumbnailURL: String,
): ContentUiModel

