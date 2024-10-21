package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComponentDTO(
    @SerialName("data")
    val componentList: List<Component?>? = listOf()
) {
    @Serializable
    data class Component(
        @SerialName("contents")
        val contents: Contents? = Contents(),
        @SerialName("footer")
        val footer: Footer? = Footer(),
        @SerialName("header")
        val header: Header? = Header()
    )
}