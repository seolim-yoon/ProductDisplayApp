package com.example.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Footer(
    @SerialName("iconURL")
    val iconURL: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("type")
    val type: String? = ""
)