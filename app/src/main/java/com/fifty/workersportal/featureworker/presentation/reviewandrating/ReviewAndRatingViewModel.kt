package com.fifty.workersportal.featureworker.presentation.reviewandrating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.domain.state.StandardTextFieldState
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class ReviewAndRatingViewModel @Inject constructor(

) : ViewModel() {

    private val _reviewTextFieldState = mutableStateOf(StandardTextFieldState())
    val reviewTextFieldState: State<StandardTextFieldState> = _reviewTextFieldState

    private val _ratingState = mutableStateOf(0f)
    val ratingState: State<Float> = _ratingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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

            ReviewAndRatingEvent.PostReviewAndRating -> {

            }
        }
    }
}