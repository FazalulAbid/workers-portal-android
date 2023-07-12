package com.fifty.workersportal.featureworker.presentation.workerprofile

import com.fifty.workersportal.featureworker.domain.model.SampleWork

sealed class WorkerProfileEvent {
    data class ClickSampleWork(val sampleWork: SampleWork) : WorkerProfileEvent()
    object ToggleFavourite : WorkerProfileEvent()
    object UpdateWorkerProfileDetails : WorkerProfileEvent()
    object UpdateSampleWorks : WorkerProfileEvent()
}
