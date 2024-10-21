package com.example.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contents(
    @SerialName("banners")
    val banners: List<Banner?>? = listOf(),
    @SerialName("goods")
    val goods: List<Goods?>? = listOf(),
    @SerialName("styles")
    val styles: List<Style?>? = listOf(),
    @SerialName("type")
    val type: String? = ""
) {
    @Serializable
    data class Banner(
        @SerialName("description")
        val description: String? = "",
        @SerialName("keyword")
        val keyword: String? = "",
        @SerialName("linkURL")
        val linkURL: String? = "",
        @SerialName("thumbnailURL")
        val thumbnailURL: String? = "",
        @SerialName("title")
        val title: String? = ""
    )

    @Serializable
    data class Goods(
        @SerialName("brandName")
        val brandName: String? = "",
        @SerialName("hasCoupon")
        val hasCoupon: Boolean? = false,
        @SerialName("linkURL")
        val linkURL: String? = "",
        @SerialName("price")
        val price: Int? = 0,
        @SerialName("saleRate")
        val saleRate: Int? = 0,
        @SerialName("thumbnailURL")
        val thumbnailURL: String? = ""
    )

    @Serializable
    data class Style(
        @SerialName("linkURL")
        val linkURL: String? = "",
        @SerialName("thumbnailURL")
        val thumbnailURL: String? = ""
    )
}