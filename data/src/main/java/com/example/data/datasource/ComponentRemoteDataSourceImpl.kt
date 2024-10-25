package com.example.data.datasource

import com.example.data.api.ComponentInfoService
import com.example.data.dto.ComponentDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComponentRemoteDataSourceImpl @Inject constructor(
    private val componentInfoService: ComponentInfoService
): ComponentRemoteDataSource {
    override suspend fun getComponentList(): Flow<ComponentDTO> = flow {
        emit(componentInfoService.getComponentList())
    }
}