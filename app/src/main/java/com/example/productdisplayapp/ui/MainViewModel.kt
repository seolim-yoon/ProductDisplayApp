package com.example.productdisplayapp.ui

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
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
import kotlinx.coroutines.launch

sealed interface ComponentEvent {
    data class Refresh(val type: ContentType): ComponentEvent
    data class LoadMore(val type: ContentType): ComponentEvent
}

sealed interface LoadState {
    data object Loading : LoadState
    data class Success(val displayComponents: List<ComponentUiModel>) : LoadState
    data class Error(val error: Throwable): LoadState
}

data class ComponentUiState(
    val loadState: LoadState = LoadState.Loading
) : MavericksState

class MainViewModel@AssistedInject constructor(
    @Assisted initialState: ComponentUiState,
    private val getComponentListUseCase: GetComponentListUseCase,
    private val componentUiMapper: ComponentUiMapper
) : MavericksViewModel<ComponentUiState>(initialState) {

    private val defaultDisplayItemCount: Int = GRID_COLUMN_DEFAULT * GRID_ROW_DEFAULT
    private val componentList: MutableList<ComponentUiModel> = mutableListOf()

    init {
        getComponentList()
    }

    private fun getComponentList() {
        viewModelScope.launch {
            setState {
                copy(
                    loadState = LoadState.Loading
                )
            }
            try {
                val componentEntityList = getComponentListUseCase()
                val componentUiModelList = componentUiMapper.mapToComponentUiModel(componentEntityList)
                componentList.addAll(componentUiModelList)
                setState {
                    copy(
                        loadState = LoadState.Success(
                            displayComponents = componentUiModelList.map { component ->
                                when (component.contentType) {
                                    ContentType.GRID, ContentType.STYLE -> component.copy(
                                        contentList = component.contentList.take(defaultDisplayItemCount)
                                    )

                                    else -> component
                                }
                            }
                        )
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(
                        loadState = LoadState.Error(
                            error = e
                        ) 
                    )
                }
            }
        }
    }

    fun onEvent(event: ComponentEvent) {
        when(event) {
            is ComponentEvent.Refresh -> refreshComponentList(event.type)
            is ComponentEvent.LoadMore -> loadMoreComponentList(event.type)
        }
    }

    private fun refreshComponentList(type: ContentType) {
        withState { state ->
            if (state.loadState is LoadState.Success) {
                val shuffledComponents = state.loadState.displayComponents.map { component ->
                    if (component.contentType == type) {
                        component.copy(
                            contentList = component.contentList.shuffled()
                        )
                    } else {
                        component
                    }
                }
                setState {
                    copy(
                        loadState = LoadState.Success(
                            displayComponents = shuffledComponents
                        )
                    )
                }
            }
        }
    }

    private fun loadMoreComponentList(type: ContentType) {
        withState { state ->
            if (state.loadState is LoadState.Success) {
                val currentDisplayComponents = state.loadState.displayComponents
                val currentDisplayContentListSize = currentDisplayComponents.find { it.contentType == type }?.contentList?.size ?: 0
                val updateDisplayContentListSize = currentDisplayContentListSize + GRID_COLUMN_DEFAULT

                val allContentList = componentList.find { it.contentType == type }?.contentList
                val updateContentList = allContentList?.take(updateDisplayContentListSize) ?: listOf()

                val displayComponents = currentDisplayComponents.map { component ->
                    if (component.contentType == type) {
                        component.copy(
                            contentList = updateContentList,
                            footerUiModel = if (updateDisplayContentListSize >= (allContentList?.size ?: 0)) {
                                component.footerUiModel.copy(footerType = FooterType.NONE)
                            } else component.footerUiModel
                        )
                    } else
                        component
                }
                setState {
                    copy(
                        loadState = LoadState.Success(
                            displayComponents = displayComponents
                        )
                    )
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