package com.example.data.repository

import com.example.data.datasource.ComponentRemoteDataSource
import com.example.data.mapper.ComponentEntityMapper
import com.example.domain.model.ComponentEntity
import com.example.domain.repository.ComponentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComponentRepositoryImpl @Inject constructor(
    private val componentRemoteDataSource: ComponentRemoteDataSource,
    private val componentEntityMapper: ComponentEntityMapper
) : ComponentRepository {
    override suspend fun getComponentList(): Flow<List<ComponentEntity>> =
        flow {
            componentRemoteDataSource.getComponentList().collect {
                emit(componentEntityMapper.mapToComponentEntity(it.componentList))
            }
        }
}