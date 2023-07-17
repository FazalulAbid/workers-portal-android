package com.fifty.fixitnow.featureworker.presentation.selectworkercategory

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.presentation.PagingState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.DefaultPaginator
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.usecase.SearchCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchCategoryState())
    val searchState: State<SearchCategoryState> = _searchState

    private val _selectedCategoryState = mutableStateOf<Category?>(null)
    val selectedCategoryState: State<Category?> = _selectedCategoryState

    var pagingState by mutableStateOf(PagingState<Category>())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private var searchJob: Job? = null

    private val paginator = DefaultPaginator(
        initialKey = pagingState.page,
        onLoadUpdated = {
            pagingState = pagingState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            searchCategoriesUseCase(
                page = nextPage,
                searchQuery = _searchFieldState.value.text.trim(),
            )
        },
        getNextKey = {
            pagingState.page + 1
        },
        onError = {
            pagingState = pagingState.copy(error = it)
            _eventFlow.emit(UiEvent.MakeToast(it))
        },
        onSuccess = { items, newKey ->
            pagingState = pagingState.copy(
                items = pagingState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextWorkers()
    }

    fun onEvent(event: SearchCategoryEvent) {
        when (event) {
            is SearchCategoryEvent.Query -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    _searchFieldState.value = searchFieldState.value.copy(
                        text = event.query
                    )
                    delay(Constants.SEARCH_DELAY)
                    loadNextWorkers(true)
                }
            }

            is SearchCategoryEvent.SelectCategory -> {
                _selectedCategoryState.value = event.category
            }
        }
    }

    fun loadNextWorkers(resetPaging: Boolean = false) {
        if (resetPaging) {
            paginator.reset()
            pagingState = PagingState()
        }
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}