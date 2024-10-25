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
    val displayComponents: List<ComponentUiModel> = listOf()
) : MavericksState

class MainViewModel@AssistedInject constructor(
    @Assisted initialState: ComponentUiState,
    private val getComponentListUseCase: GetComponentListUseCase,
    private val componentUiMapper: ComponentUiMapper
) : MavericksViewModel<ComponentUiState>(initialState) {

    private val defaultDisplayItemCount: Int = GRID_COLUMN_DEFAULT * GRID_ROW_DEFAULT
    private val componentList: MutableList<ComponentUiModel> = mutableListOf()

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
                .execute { async ->
                    val components = componentUiMapper.mapToComponentUiModel(async() ?: listOf())
                    when (async) {
                        is Success -> {
                            componentList.addAll(components)
                            copy(
                                displayComponents = components.map { component ->
                                    when (component.contentType) {
                                        ContentType.GRID, ContentType.STYLE -> component.copy(
                                            contentList = component.contentList.take(defaultDisplayItemCount)
                                        )
                                        else -> component
                                    }
                                }
                            )
                        }

                        is Fail -> {
                            _effect.trySend(ComponentUiEffect.ShowToastMsg(errorMsg = async.error.message.orEmpty()))
                            copy()
                        }

                        else -> copy()
                    }
                }
        }
    }

    fun refreshComponentList(type: ContentType) {
        withState { state ->
            val shuffledComponents = state.displayComponents.map { component ->
                if (component.contentType == type) {
                    component.copy(
                        contentList = component.contentList.shuffled()
                    )
                } else {
                    component
                }
            }
            setState {
                copy(displayComponents = shuffledComponents)
            }
        }
    }

    fun loadMoreComponentList(type: ContentType) {
        withState { state ->
            val displayContentListSize = state.displayComponents.find { it.contentType == type }?.contentList?.size ?: 0
            val updateDisplayContentListSize = displayContentListSize + GRID_COLUMN_DEFAULT

            val allContentList = componentList.find { it.contentType == type }?.contentList

            val displayComponents = state.displayComponents.map { component ->
                if (component.contentType == type) {
                    component.copy(
                        contentList = allContentList?.take(updateDisplayContentListSize) ?: listOf(),
                        footerUiModel = if (updateDisplayContentListSize >= (allContentList?.size ?: 0)) {
                            component.footerUiModel.copy(footerType = FooterType.NONE)
                        } else component.footerUiModel
                    )
                } else
                    component
            }

            setState {
              copy(displayComponents = displayComponents)
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel, ComponentUiState> {
        override fun create(state: ComponentUiState): MainViewModel
    }

    companion object : MavericksViewModelFactory<MainViewModel, ComponentUiState> by hiltMavericksViewModelFactory()
}