package com.fifty.fixitnow.featurechat.presentation.message

import com.fifty.fixitnow.featureworker.domain.model.Worker

data class MessageState(
    val worker: Worker? = null,
    val isLoading: Boolean = false,
    val isMessageSending: Boolean = false
)
