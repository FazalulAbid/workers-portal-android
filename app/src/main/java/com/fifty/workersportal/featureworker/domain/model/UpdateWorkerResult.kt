package com.fifty.workersportal.featureworker.domain.model

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.util.WorkerError

data class UpdateWorkerResult(
    val firstNameError: WorkerError? = null,
    val emailError: WorkerError? = null,
    val bioError: WorkerError? = null,
    val ageError: WorkerError? = null,
    val skillsError: WorkerError? = null,
    val skillsWageError: WorkerError? = null,
    val result: SimpleResource? = null
)
