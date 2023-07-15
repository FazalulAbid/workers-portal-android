package com.fifty.workersportal.featureworker.presentation.workerprofile

import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.model.Worker

data class WorkerProfileState(
    val clickedSampleWork: SampleWork? = null,
    val worker: Worker? = null,
    val isOwnProfile: Boolean = false,
    val isLoading: Boolean = false,
    val isSampleWorksLoading: Boolean = false
)
