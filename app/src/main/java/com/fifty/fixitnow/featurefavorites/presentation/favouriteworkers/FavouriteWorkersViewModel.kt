package com.fifty.fixitnow.featurefavorites.presentation.favouriteworkers

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.presentation.PagingState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.DefaultPaginator
import com.fifty.fixitnow.core.util.FavouriteToggle
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.usecase.GetFavouritesUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteWorkersViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val toggleFavouriteWorkerUseCase: ToggleFavouriteWorkerUseCase,
    private val favouriteToggle: FavouriteToggle
) : ViewModel() {

    private val _state = mutableStateOf(FavouriteWorkersState())
    val state: State<FavouriteWorkersState> = _state

    var pagingState by mutableStateOf(PagingState<Worker>())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = pagingState.page,
        onLoadUpdated = {
            pagingState = pagingState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            getFavouritesUseCase(
                page = nextPage
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

    fun onEvent(event: FavouriteWorkersEvent) {
        when (event) {
            is FavouriteWorkersEvent.ToggleFavouriteWorkers -> {
                toggleFavouriteWorker(event.workerId)
            }

            FavouriteWorkersEvent.LoadFavouriteWorkers -> {
                loadNextWorkers(resetPaging = true)
            }
        }
    }

    private fun toggleFavouriteWorker(workerId: String) {
        viewModelScope.launch {
            favouriteToggle.toggleFavourite(
                workers = pagingState.items,
                workerId = workerId,
                onRequest = { isFavourite ->
                    toggleFavouriteWorkerUseCase(userId = workerId, isFavourite = isFavourite)
                },
                onStateUpdated = { workers ->
                    pagingState = pagingState.copy(
                        items = workers
                    )
                }
            )
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