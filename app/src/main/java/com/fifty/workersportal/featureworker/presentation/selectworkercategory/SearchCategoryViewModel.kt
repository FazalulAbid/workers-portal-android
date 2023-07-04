package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.usecase.SearchCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchCategoryState())
    val searchState: State<SearchCategoryState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var searchedCategories: Flow<PagingData<Category>> = flowOf(PagingData.empty())

    private var searchJob: Job? = null

    fun onEvent(event: SearchCategoryEvent) {
        when (event) {
            is SearchCategoryEvent.Query -> {
                _search.value = event.query
            }
        }
    }

    private val _search = MutableStateFlow("")

    val search = _search.asStateFlow()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val categorySearchResults = search.debounce(Constants.SEARCH_DELAY).flatMapLatest { query ->
        searchCategoriesUseCase(query).cachedIn(viewModelScope)
    }
}