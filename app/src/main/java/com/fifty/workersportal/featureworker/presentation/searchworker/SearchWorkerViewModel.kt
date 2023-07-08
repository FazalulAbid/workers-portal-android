package com.fifty.workersportal.featureworker.presentation.searchworker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchWorkerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _sortState = mutableStateOf(SearchWorkerSortState())
    val sortState: State<SearchWorkerSortState> = _sortState

    private val _tempSortState = mutableStateOf(SearchWorkerSortState())
    val tempSortState: State<SearchWorkerSortState> = _tempSortState

    private val _filterState = mutableStateOf(SearchWorkerFilterState())
    val filterState: State<SearchWorkerFilterState> = _filterState

    fun onEvent(event: SearchWorkerEvent) {
        when (event) {
            is SearchWorkerEvent.Query -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.query
                )
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
}