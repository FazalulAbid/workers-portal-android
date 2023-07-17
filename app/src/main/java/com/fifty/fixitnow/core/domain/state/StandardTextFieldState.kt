package com.fifty.fixitnow.core.domain.state

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)