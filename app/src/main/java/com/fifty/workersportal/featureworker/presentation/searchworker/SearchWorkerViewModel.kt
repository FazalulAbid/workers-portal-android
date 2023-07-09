package com.fifty.workersportal.featureworker.presentation.searchworker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.PagingState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.DefaultPaginator
import com.fifty.workersportal.core.util.FavouriteToggle
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.usecase.GetSearchedSortedAndFilteredWorkersUseCase
import com.fifty.workersportal.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWorkerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val toggleFavouriteWorkerUseCase: ToggleFavouriteWorkerUseCase,
    private val favouriteToggle: FavouriteToggle,
    private val getSearchedSortedAndFilteredWorkersUseCase: GetSearchedSortedAndFilteredWorkersUseCase
) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _sortState = mutableStateOf(SearchWorkerSortState())
    val sortState: State<SearchWorkerSortState> = _sortState

    private val _tempSortState = mutableStateOf(SearchWorkerSortState())
    val tempSortState: State<SearchWorkerSortState> = _tempSortState

    private val _filterState = mutableStateOf(SearchWorkerFilterState())
    val filterState: State<SearchWorkerFilterState> = _filterState

    private val _pagingState = mutableStateOf(PagingState<Worker>())
    val pagingState: State<PagingState<Worker>> = _pagingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            getSearchedSortedAndFilteredWorkersUseCase(
                page = page,
                query = _searchFieldState.value.text.trim()
            )
        },
        onSuccess = { workers ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + workers,
                endReached = workers.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.MakeToast(uiText))
        }
    )

    init {
        loadNextWorkers()
    }

    fun onEvent(event: SearchWorkerEvent) {
        when (event) {
            is SearchWorkerEvent.Query -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.query
                )
            }

            is SearchWorkerEvent.AddToFavourite -> {
                addWorkerToFavourites(event.workerId)
            }

            SearchWorkerEvent.ClickClearAllSortAndFilters -> {
                _filterState.value = SearchWorkerFilterState()
                _tempSortState.value = SearchWorkerSortState()
                _sortState.value = SearchWorkerSortState()
            }

            SearchWorkerEvent.ToggleNearestSort -> {
                _tempSortState.value = tempSortState.value.copy(
                    isDistanceLowToHigh = !tempSortState.value.isDistanceLowToHigh
                )
            }

            SearchWorkerEvent.ClickSortApply -> {
                _sortState.value = _tempSortState.value
            }

            SearchWorkerEvent.SelectCostHighToLowSort -> {
                _tempSortState.value = SearchWorkerSortState(isWageHighToLow = true)
            }

            SearchWorkerEvent.SelectCostLowToHighSort -> {
                _tempSortState.value = SearchWorkerSortState(isWageLowToHigh = true)
            }

            SearchWorkerEvent.SelectDistanceLowToHighSort -> {
                _tempSortState.value = SearchWorkerSortState(isDistanceLowToHigh = true)
            }

            SearchWorkerEvent.SelectRatingHighToLowSort -> {
                _tempSortState.value = SearchWorkerSortState(isRatingHighToLow = true)
            }

            is SearchWorkerEvent.TogglePreviouslyHiredFilter -> {
                _filterState.value = filterState.value.copy(
                    isPreviouslyHired = !filterState.value.isPreviouslyHired
                )
            }

            is SearchWorkerEvent.ToggleRatingFourPlusFilter -> {
                _filterState.value = filterState.value.copy(
                    isRatingFourPlus = !filterState.value.isRatingFourPlus
                )
            }

            SearchWorkerEvent.OnSheetDismiss -> {
                _tempSortState.value = _sortState.value
            }
        }
    }

    private fun addWorkerToFavourites(workerId: String) {
        viewModelScope.launch {
            favouriteToggle.toggleFavourite(
                workers = pagingState.value.items,
                workerId = workerId,
                onRequest = { isFavourite ->
                    toggleFavouriteWorkerUseCase(userId = workerId, isFavourite = isFavourite)
                },
                onStateUpdated = { workers ->
                    _pagingState.value = pagingState.value.copy(
                        items = workers
                    )
                }
            )
        }
    }

    fun loadNextWorkers() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}