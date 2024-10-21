package com.example.data.datasource

import com.example.data.api.ComponentInfoService
import com.example.data.dto.ComponentDTO
import javax.inject.Inject

class ComponentRemoteDataSourceImpl @Inject constructor(
    private val componentInfoService: ComponentInfoService
): ComponentRemoteDataSource {
    override suspend fun getComponentList(): ComponentDTO = componentInfoService.getComponentList()
}