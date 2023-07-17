package com.fifty.fixitnow.featureworker.presentation.workerprofile

import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.model.Worker

data class WorkerProfileState(
    val clickedSampleWork: SampleWork? = null,
    val worker: Worker? = null,
    val isOwnProfile: Boolean = false,
    val isLoading: Boolean = false,
    val isSampleWorksLoading: Boolean = false
)
