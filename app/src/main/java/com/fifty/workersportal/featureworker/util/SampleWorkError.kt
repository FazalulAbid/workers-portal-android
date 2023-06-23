package com.fifty.workersportal.featureworker.util

import com.fifty.workersportal.core.util.Error

sealed class SampleWorkError : Error() {
    object NoImageError : SampleWorkError()
}