package com.fifty.fixitnow.featureworker.util

import com.fifty.fixitnow.core.util.Error

sealed class SampleWorkError : Error() {
    object NoImageError : SampleWorkError()
}