package com.example.data.repository

import com.example.data.datasource.ComponentRemoteDataSource
import com.example.data.mapper.ComponentEntityMapper
import com.example.domain.model.ComponentEntity
import com.example.domain.repository.ComponentRepository
import javax.inject.Inject

class ComponentRepositoryImpl @Inject constructor(
    private val componentRemoteDataSource: ComponentRemoteDataSource,
    private val componentEntityMapper: ComponentEntityMapper
): ComponentRepository {
    override suspend fun getComponentList(): List<ComponentEntity> =
        componentEntityMapper.mapToComponentEntity(componentRemoteDataSource.getComponentList().componentList)
}