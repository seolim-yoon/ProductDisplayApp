package com.example.productdisplayapp.uimodel

sealed class ContentUiModel

data class BannerUiModel(
    val id: Int,
    val linkURL: String,
    val thumbnailURL: String,
    val title: String,
    val description: String,
    val keyword: String
): ContentUiModel()

data class GoodsUiModel(
    val id: Int,
    val linkURL: String,
    val thumbnailURL: String,
    val brandName: String,
    val price: Int,
    val saleRate: Int,
    val hasCoupon: Boolean
): ContentUiModel()

data class StyleUiModel(
    val id: Int,
    val linkURL: String,
    val thumbnailURL: String
): ContentUiModel()

