package com.fifty.workersportal.featureworker.presentation.workerlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import javax.inject.Inject

class WorkerListViewModel @Inject constructor(

) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    fun onEvent(event: WorkerListEvent) {
        when (event) {
            is WorkerListEvent.Query -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.query
                )
            }
        }
    }
}