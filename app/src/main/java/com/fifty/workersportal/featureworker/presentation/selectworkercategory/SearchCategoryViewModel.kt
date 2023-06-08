package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.usecase.WorkerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val workerUseCases: WorkerUseCases
) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _searchState = mutableStateOf(SearchCategoryState())
    val searchState: State<SearchCategoryState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val searchJob: Job? = null

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            _searchState.value = searchState.value.copy(
                isLoading = true
            )
            when (val result = workerUseCases.getCategories()) {
                is Resource.Success -> {
                    _searchState.value = searchState.value.copy(
                        workerCategories = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _searchState.value = searchState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: SearchCategoryEvent) {
        when (event) {
            is SearchCategoryEvent.Query -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.query
                )
            }
        }
    }
}