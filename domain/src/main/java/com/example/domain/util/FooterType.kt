package com.example.domain.util


enum class FooterType {
    REFRESH, MORE, NONE;

    companion object {
        fun fromValue(value: String): FooterType {
            return FooterType.entries.find { it.name == value } ?: NONE
        }
    }
}