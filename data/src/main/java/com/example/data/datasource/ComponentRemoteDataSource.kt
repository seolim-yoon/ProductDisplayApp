package com.example.data.datasource

import com.example.data.dto.ComponentDTO
import kotlinx.coroutines.flow.Flow

interface ComponentRemoteDataSource {
    suspend fun getComponentList(): Flow<ComponentDTO>
}