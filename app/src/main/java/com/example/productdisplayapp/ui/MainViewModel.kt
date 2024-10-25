package com.example.productdisplayapp.ui

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.domain.usecase.GetComponentListUseCase
import com.example.domain.util.ContentType
import com.example.domain.util.FooterType
import com.example.productdisplayapp.mapper.ComponentUiMapper
import com.example.productdisplayapp.uimodel.ComponentUiModel
import com.example.productdisplayapp.util.GRID_COLUMN_DEFAULT
import com.example.productdisplayapp.util.GRID_ROW_DEFAULT
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ComponentUiEffect {
    data class ShowToastMsg(val errorMsg: String): ComponentUiEffect
}

data class ComponentUiState(
    val defaultDisplayGridItemCount: Int = GRID_COLUMN_DEFAULT * GRID_ROW_DEFAULT,
    val defaultDisplayStyleItemCount: Int = GRID_COLUMN_DEFAULT * GRID_ROW_DEFAULT,
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
                .catch { e ->
                    _effect.send(ComponentUiEffect.ShowToastMsg(errorMsg = e.message.orEmpty()))
                }
                .execute {
                    when(it) {
                        is Success -> copy(components = componentUiMapper.mapToComponentUiModel(it()))
                        is Fail -> {
                            _effect.trySend(ComponentUiEffect.ShowToastMsg(errorMsg = it.error.message.orEmpty()))
                            copy()
                        }
                        else -> copy()
                    }
                 }
        }
    }

    fun refreshComponentList(type: ContentType) {
        withState { state ->
            val shuffledComponents = state.components.map { component ->
                component.takeIf { it.contentType == type }
                    ?.copy(contentList = component.contentList.shuffled())
                    ?: component
            }
            setState {
                copy(components = shuffledComponents)
            }
        }
    }

    fun loadMoreComponentList(type: ContentType) {
        withState { state ->
            val currentComponent = state.components.find { it.contentType == type }
            val currentContentListSize = currentComponent?.contentList?.size ?: 0

            val updateDisplayItemCount =
                when (type) {
                    ContentType.GRID -> { state.defaultDisplayGridItemCount }
                    ContentType.STYLE -> { state.defaultDisplayStyleItemCount }
                    else -> { 0 }
                } + GRID_COLUMN_DEFAULT

            val updateFooterTypeComponents =
                state.components.map { component ->
                    if (component.contentType == type && updateDisplayItemCount >= currentContentListSize) {
                        component.copy(
                            footerUiModel = component.footerUiModel.copy(footerType = FooterType.NONE)
                        )
                    } else {
                        component
                    }
                }

            setState {
                when(type) {
                    ContentType.GRID -> {
                        copy(
                            components = updateFooterTypeComponents,
                            defaultDisplayGridItemCount = updateDisplayItemCount,
                        )
                    }
                    ContentType.STYLE -> {
                        copy(
                            components = updateFooterTypeComponents,
                            defaultDisplayStyleItemCount = updateDisplayItemCount
                        )
                    }
                    else -> {
                        copy()
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel, ComponentUiState> {
        override fun create(state: ComponentUiState): MainViewModel
    }

    companion object : MavericksViewModelFactory<MainViewModel, ComponentUiState> by hiltMavericksViewModelFactory()
}