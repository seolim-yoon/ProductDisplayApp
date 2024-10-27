package com.example.data.datasource

import android.content.Context
import com.example.data.api.ComponentInfoService
import com.example.data.dto.ComponentDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ComponentRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val componentInfoService: ComponentInfoService
) : ComponentRemoteDataSource {
    override suspend fun getComponentList(): ComponentDTO = componentInfoService.getComponentList()

    override suspend fun getMockComponentList(): ComponentDTO {
        val jsonString = context.assets.open("product_mock_response.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}