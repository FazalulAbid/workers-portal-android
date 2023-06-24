package com.fifty.workersportal.featureworker.presentation.workerprofile

import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureworker.domain.model.SampleWork

data class WorkerProfileState(
    val profile: Profile? = null,
    val isOwnProfile: Boolean = false,
    val isLoading: Boolean = false,
    val isSampleWorksLoading: Boolean = false
)
