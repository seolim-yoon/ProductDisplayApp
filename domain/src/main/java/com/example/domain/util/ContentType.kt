package com.example.domain.util

enum class ContentType {
    BANNER, GRID, SCROLL, STYLE, NONE;

    companion object {
        fun fromValue(value: String): ContentType {
            return entries.find { it.name == value } ?: NONE
        }
    }
}