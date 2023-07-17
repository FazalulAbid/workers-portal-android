package com.fifty.fixitnow.featureworker.domain.model

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.util.SampleWorkError

data class PostSampleWorkResult(
    val imageError: SampleWorkError? = null,
    val unknownError: SampleWorkError? = null,
    val result: Resource<SampleWork>? = null
)
