package com.fifty.workersportal.featureworker.presentation.workerprofile

sealed class WorkerProfileEvent {
    object UpdateWorkerProfileDetails : WorkerProfileEvent()
    object UpdateSampleWorks : WorkerProfileEvent()
}
