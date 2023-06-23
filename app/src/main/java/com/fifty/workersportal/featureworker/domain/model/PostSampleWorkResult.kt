package com.fifty.workersportal.featureworker.domain.model

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.util.SampleWorkError

data class PostSampleWorkResult(
    val imageError: SampleWorkError? = null,
    val unknownError: SampleWorkError? = null,
    val result: Resource<SampleWork>? = null
)
