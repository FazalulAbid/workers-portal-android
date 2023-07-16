package com.fifty.workersportal.featureworker.presentation.reviewandrating

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.usecase.GetReviewAndRatingUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetWorkerRatingsCountUseCase
import com.fifty.workersportal.featureworker.domain.usecase.PostReviewAndRatingUseCase
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewAndRatingViewModel @Inject constructor(
    private val postReviewAndRatingUseCase: PostReviewAndRatingUseCase,
    private val getReviewAndRatingUseCase: GetReviewAndRatingUseCase,
    private val getWorkerRatingsCountUseCase: GetWorkerRatingsCountUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reviewTextFieldState = mutableStateOf(StandardTextFieldState())
    val reviewTextFieldState: State<StandardTextFieldState> = _reviewTextFieldState

    private val _state = mutableStateOf(ReviewAndRatingState())
    val state: State<ReviewAndRatingState> = _state

    private val _ratingState = mutableStateOf(0)
    val ratingState: State<Int> = _ratingState

    var reviewsAndRatings: Flow<PagingData<ReviewAndRating>> = flowOf(PagingData.empty())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<ReviewAndRatingError>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val workerId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            getReviewAndRating(workerId)
            getWorkerRatingsCount(workerId)
        }
    }

    fun onEvent(event: ReviewAndRatingEvent) {
        when (event) {
            is ReviewAndRatingEvent.RatingValueChange -> {
                _ratingState.value = event.rating
            }

            is ReviewAndRatingEvent.ReviewChanged -> {
                _reviewTextFieldState.value = reviewTextFieldState.value.copy(
                    text = event.review
                )
            }

            is ReviewAndRatingEvent.PostReviewAndRating -> {
                postReviewAndRating()
            }
        }
    }

    private fun getReviewAndRating(workerId: String) {
        _state.value = state.value.copy(isLoading = true)
        reviewsAndRatings = getReviewAndRatingUseCase(workerId).cachedIn(viewModelScope)
        _state.value = state.value.copy(isLoading = false)
    }

    private suspend fun getWorkerRatingsCount(workerId: String) {
        _state.value = state.value.copy(isLoading = true)
        when (val result = getWorkerRatingsCountUseCase(workerId = workerId)) {
            is Resource.Success -> {
                _state.value = state.value.copy(
                    workerRatingsCount = result.data,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    private fun postReviewAndRating() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val postReviewAndRatingResult = postReviewAndRatingUseCase(
                ratedUserId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase(),
                rating = ratingState.value.toFloat(),
                review = reviewTextFieldState.value.text,
                isWorker = true
            )
            if (postReviewAndRatingResult.ratingError != null) {
                _errorFlow.emit(
                    postReviewAndRatingResult.ratingError
                )
            } else if (postReviewAndRatingResult.reviewError != null) {
                _errorFlow.emit(
                    postReviewAndRatingResult.reviewError
                )
            } else {
                when (postReviewAndRatingResult.result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                        _ratingState.value = 0
                        _reviewTextFieldState.value = StandardTextFieldState()
                        _eventFlow.emit(UiEvent.ReviewAndRatingPosted)
                        getReviewAndRating(
                            savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.MakeToast(
                                postReviewAndRatingResult.result.uiText ?: UiText.unknownError()
                            )
                        )
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                    }

                    null -> {
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}