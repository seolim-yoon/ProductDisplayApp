package com.example.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("iconURL")
    val iconURL: String? = "",
    @SerialName("linkURL")
    val linkURL: String? = "",
    @SerialName("title")
    val title: String? = ""
)