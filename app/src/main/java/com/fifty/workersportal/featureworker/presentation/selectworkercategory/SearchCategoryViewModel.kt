package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.SearchCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _searchState = mutableStateOf(SearchCategoryState())
    val searchState: State<SearchCategoryState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var searchedCategories: Flow<PagingData<Category>> = flowOf(PagingData.empty())

    private var searchJob: Job? = null

    init {
        searchCategories(_searchFieldState.value.text)
    }

    fun onEvent(event: SearchCategoryEvent) {
        when (event) {
            is SearchCategoryEvent.Query -> {
                searchCategories(event.query)
            }
        }
    }

    private fun searchCategories(searchQuery: String) {
        _searchState.value = searchState.value.copy(
            isLoading = true
        )
        _searchFieldState.value = searchFieldState.value.copy(
            text = searchQuery
        )
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(Constants.SEARCH_DELAY)
            searchedCategories = searchCategoriesUseCase(searchQuery).cachedIn(viewModelScope)
            _searchState.value = searchState.value.copy(
                isLoading = false
            )
        }
    }
}