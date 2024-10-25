package com.example.domain.usecase

import com.example.domain.model.ComponentEntity
import com.example.domain.repository.ComponentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComponentListUseCase @Inject constructor(
    private val componentRepository: ComponentRepository
) {
    suspend operator fun invoke(): Flow<List<ComponentEntity>> =
        componentRepository.getComponentList()
}