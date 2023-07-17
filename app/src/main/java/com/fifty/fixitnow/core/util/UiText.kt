package com.fifty.fixitnow.core.util

import androidx.annotation.StringRes
import com.fifty.fixitnow.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.unknown_error)
        }
    }
}