package com.example.data.datasource

import android.content.Context
import com.example.data.api.ComponentInfoService
import com.example.data.dto.ComponentDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ComponentRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val componentInfoService: ComponentInfoService
): ComponentRemoteDataSource {
    override suspend fun getComponentList(): Flow<ComponentDTO> = flow {
        emit(componentInfoService.getComponentList())
    }

    override suspend fun getMockComponentList(): Flow<ComponentDTO> = flow {
        val jsonString = context.assets.open("product_mock_response.json").bufferedReader().use { it.readText() }
        emit(Json.decodeFromString(jsonString))
    }
}