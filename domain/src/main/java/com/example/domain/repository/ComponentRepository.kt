package com.example.domain.repository

import com.example.domain.model.ComponentEntity

interface ComponentRepository {
    suspend fun getComponentList(): List<ComponentEntity>
}