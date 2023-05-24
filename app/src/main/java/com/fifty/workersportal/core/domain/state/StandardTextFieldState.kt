package com.fifty.workersportal.core.domain.state

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)