package com.example.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class Footer(
    val iconURL: String? = "",
    val title: String? = "",
    val type: String? = ""
)