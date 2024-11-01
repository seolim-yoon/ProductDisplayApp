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
        val contents: Contents? = Contents(),
        val footer: Footer? = Footer(),
        val header: Header? = Header()
    )
}