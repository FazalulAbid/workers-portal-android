package com.fifty.fixitnow.featurechat.presentation.message

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(

) : ViewModel() {

    init {

    }

    fun onEvent(event: MessageEvent) {
        when (event) {
            MessageEvent.Test -> {
                Log.d("Hello", "onEvent: Message ViewModel Test")
            }
        }
    }
}