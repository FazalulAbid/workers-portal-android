package com.fifty.workersportal.featureworker.presentation.postsamplework

import android.net.Uri

sealed class PostSampleWorkEvent {
    data class EnterPostTitle(val title: String) : PostSampleWorkEvent()
    data class EnterPostDescription(val description: String) : PostSampleWorkEvent()
    data class PickImage(val uri: Uri?) : PostSampleWorkEvent()
    object Post : PostSampleWorkEvent()
}