package com.fifty.fixitnow.featureworker.presentation.postsamplework

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.domain.state.StandardTextFieldState
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.usecase.PostSampleWorkUseCase
import com.fifty.fixitnow.featureworker.util.SampleWorkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostSampleWorkViewModel @Inject constructor(
    private val postSampleWorkUseCase: PostSampleWorkUseCase
) : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _postImageState = mutableStateOf<Uri?>(null)
    val postImageState: State<Uri?> = _postImageState

    private val _state = mutableStateOf(PostSampleWorkState())
    val state: State<PostSampleWorkState> = _state

    private val _errorFlow = MutableSharedFlow<SampleWorkError>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: PostSampleWorkEvent) {
        when (event) {
            is PostSampleWorkEvent.EnterPostDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.description
                )
            }

            is PostSampleWorkEvent.EnterPostTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }

            is PostSampleWorkEvent.PickImage -> {
                _postImageState.value = event.uri
            }

            PostSampleWorkEvent.Post -> {
                postSampleWork()
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

    private fun postSampleWork() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val postSampleWorkResult = postSampleWorkUseCase(
                sampleWork = SampleWork(
                    title = _titleState.value.text,
                    description = _descriptionState.value.text
                ),
                imageUri = postImageState.value
            )

            if (postSampleWorkResult.imageError != null) {
                _errorFlow.emit(
                    postSampleWorkResult.imageError
                )
            } else {
                when (val result = postSampleWorkResult.result) {
                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.NavigateUp
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.MakeToast(result.uiText ?: UiText.unknownError())
                        )
                    }

                    null -> Unit
                }
            }
            _state.value = state.value.copy(
                isLoading = false
            )
        }
    }
}