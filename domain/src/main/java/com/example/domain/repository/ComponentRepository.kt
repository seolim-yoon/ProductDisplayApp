package com.example.domain.repository

import com.example.domain.model.ComponentEntity
import kotlinx.coroutines.flow.Flow

interface ComponentRepository {
    suspend fun getComponentList(): Flow<List<ComponentEntity>>
}