package com.fifty.fixitnow.featureuser.presentation.userdashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.PagingState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.DefaultPaginator
import com.fifty.fixitnow.core.util.FavouriteToggle
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureuser.domain.usecase.GetDashboardBannersUseCase
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.usecase.GetSearchedSortedAndFilteredWorkersUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.GetSuggestedCategoriesUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val getDashboardBannersUseCase: GetDashboardBannersUseCase,
    private val getCategoriesUseCase: GetSuggestedCategoriesUseCase,
    private val toggleFavouriteWorkerUseCase: ToggleFavouriteWorkerUseCase,
    private val favouriteToggle: FavouriteToggle,
    private val getSearchedSortedAndFilteredWorkersUseCase: GetSearchedSortedAndFilteredWorkersUseCase
) : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _state = mutableStateOf(UserDashboardState())
    val state: State<UserDashboardState> = _state

    private val _selectedCategoryState = mutableStateOf<Category?>(null)
    val selectedCategoryState: State<Category?> = _selectedCategoryState

    private val _suggestedCategoriesState = mutableStateOf(SuggestedCategoriesState())
    val suggestedCategoriesState: State<SuggestedCategoriesState> = _suggestedCategoriesState

    var pagingState by mutableStateOf(PagingState<Worker>())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = pagingState.page,
        onLoadUpdated = {
            pagingState = pagingState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            getSearchedSortedAndFilteredWorkersUseCase(
                page = nextPage,
                query = "",
                categoryId = null
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
        getBanners()
        getSuggestedCategories()
        loadNextWorkers()
    }

    fun onEvent(event: UserDashboardEvent) {
        when (event) {
            is UserDashboardEvent.ToggleFavouriteWorker -> {
                toggleFavouriteWorker(event.workerId)
            }

            is UserDashboardEvent.SelectWorkerCategory -> {
                _selectedCategoryState.value = event.category
            }

            UserDashboardEvent.UpdateSelectedAddress -> {
                getLocalAddress()
            }
        }
    }

    fun dismissPermissionDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        } else {
            viewModelScope.launch {
                _eventFlow.emit(
                    UiEvent.Navigate(
                        Screen.SelectLocationScreen.route
                    )
                )
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

    private fun getSuggestedCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Resource.Success -> {
                    _suggestedCategoriesState.value = suggestedCategoriesState.value.copy(
                        suggestedCategories = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            when (val result = getDashboardBannersUseCase()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        banners = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun getLocalAddress() {
        _state.value = state.value.copy(
            selectedLocalAddress = Session.selectedAddress.value
        )
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