package com.example.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val iconURL: String? = "",
    val linkURL: String? = "",
    val title: String? = ""
)