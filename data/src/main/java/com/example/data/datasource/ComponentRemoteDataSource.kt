package com.example.data.datasource

import com.example.data.dto.ComponentDTO

interface ComponentRemoteDataSource {
    suspend fun getComponentList(): ComponentDTO

    suspend fun getMockComponentList(): ComponentDTO
}