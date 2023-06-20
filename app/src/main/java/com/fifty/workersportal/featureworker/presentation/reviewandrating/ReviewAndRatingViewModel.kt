package com.fifty.workersportal.featureworker.presentation.reviewandrating

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworker.domain.usecase.PostReviewAndRatingUseCase
import com.fifty.workersportal.featureworker.util.ReviewAndRatingError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewAndRatingViewModel @Inject constructor(
    private val postReviewAndRatingUseCase: PostReviewAndRatingUseCase
) : ViewModel() {

    private val _reviewTextFieldState = mutableStateOf(StandardTextFieldState())
    val reviewTextFieldState: State<StandardTextFieldState> = _reviewTextFieldState

    private val _state = mutableStateOf(ReviewAndRatingState())
    val state: State<ReviewAndRatingState> = _state

    private val _ratingState = mutableStateOf(0f)
    val ratingState: State<Float> = _ratingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<ReviewAndRatingError>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun onEvent(event: ReviewAndRatingEvent) {
        when (event) {
            is ReviewAndRatingEvent.RatingValueChange -> {
                _ratingState.value = event.rating
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.MakeToast(UiText.DynamicString(_ratingState.value.toString()))
                    )
                }
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

    private fun postReviewAndRating() {
        Log.d("Hello", "Function worked")
        _state.value = state.value.copy(
            isLoading = true
        )
        val reviewAndRating = ReviewAndRating(
            ratedUserId = Session.userId ?: "",
            rating = _ratingState.value,
            review = reviewTextFieldState.value.text,
            isWorker = Session.isWorker
        )
        viewModelScope.launch {
            Log.d("Hello", "postReviewAndRating: viewModelScope launched")
            val postReviewAndRatingResult = postReviewAndRatingUseCase(reviewAndRating)
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
                        _eventFlow.emit(UiEvent.ReviewAndRatingPosted)
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