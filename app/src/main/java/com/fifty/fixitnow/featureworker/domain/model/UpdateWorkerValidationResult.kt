package com.fifty.fixitnow.featureworker.domain.model

import com.fifty.fixitnow.featureworker.util.ProfileError

data class UpdateWorkerValidationResult(
    val firstNameError: ProfileError? = null,
    val emailError: ProfileError? = null,
    val bioError: ProfileError? = null,
    val ageError: ProfileError? = null,
    val skillsError: ProfileError? = null,
    val primarySkillError: ProfileError? = null,
    val skillsWageError: ProfileError? = null,
    val unknownError: ProfileError? = null,
    val result: UpdateWorkerData? = null
)
