package com.example.productdisplayapp.ui

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.domain.usecase.GetComponentListUseCase
import com.example.productdisplayapp.mapper.ComponentUiMapper
import com.example.productdisplayapp.uimodel.ComponentUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ComponentUiEffect {
    object ShowToastMsg: ComponentUiEffect
}

data class ComponentUiState(
    val components: List<ComponentUiModel> = listOf()
) : MavericksState

class MainViewModel@AssistedInject constructor(
    @Assisted initialState: ComponentUiState,
    private val getComponentListUseCase: GetComponentListUseCase,
    private val componentUiMapper: ComponentUiMapper
) : MavericksViewModel<ComponentUiState>(initialState) {

    private val _effect : Channel<ComponentUiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getComponentList()
    }

    private fun getComponentList() {
        viewModelScope.launch {
            getComponentListUseCase()
                .catch {
                    _effect.send(ComponentUiEffect.ShowToastMsg)
                }
                .setOnEach { componentList ->
                    copy(components = componentUiMapper.mapToComponentUiModel(componentList))
                }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel, ComponentUiState> {
        override fun create(state: ComponentUiState): MainViewModel
    }

    companion object : MavericksViewModelFactory<MainViewModel, ComponentUiState> by hiltMavericksViewModelFactory()
}