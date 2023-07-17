package com.fifty.fixitnow.featureworker.presentation.workerprofile

import com.fifty.fixitnow.featureworker.domain.model.SampleWork

sealed class WorkerProfileEvent {
    data class ClickSampleWork(val sampleWork: SampleWork) : WorkerProfileEvent()
    object ToggleFavourite : WorkerProfileEvent()
    object UpdateWorkerProfileDetails : WorkerProfileEvent()
    object UpdateSampleWorks : WorkerProfileEvent()
}
