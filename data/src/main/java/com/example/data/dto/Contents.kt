package com.example.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class Contents(
    val banners: List<Banner?>? = listOf(),
    val goods: List<Goods?>? = listOf(),
    val styles: List<Style?>? = listOf(),
    val type: String? = ""
) {
    @Serializable
    data class Banner(
        val description: String? = "",
        val keyword: String? = "",
        val linkURL: String? = "",
        val thumbnailURL: String? = "",
        val title: String? = ""
    )

    @Serializable
    data class Goods(
        val brandName: String? = "",
        val hasCoupon: Boolean? = false,
        val linkURL: String? = "",
        val price: Int? = 0,
        val saleRate: Int? = 0,
        val thumbnailURL: String? = ""
    )

    @Serializable
    data class Style(
        val linkURL: String? = "",
        val thumbnailURL: String? = ""
    )
}