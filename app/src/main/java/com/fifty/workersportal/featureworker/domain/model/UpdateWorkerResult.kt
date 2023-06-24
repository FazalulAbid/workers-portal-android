package com.fifty.workersportal.featureworker.domain.model

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureuser.domain.model.Profile
import com.fifty.workersportal.featureworker.util.ProfileError

data class UpdateWorkerResult(
    val firstNameError: ProfileError? = null,
    val emailError: ProfileError? = null,
    val bioError: ProfileError? = null,
    val ageError: ProfileError? = null,
    val skillsError: ProfileError? = null,
    val primarySkillError: ProfileError? = null,
    val skillsWageError: ProfileError? = null,
    val unknownError: ProfileError? = null,
    val result: SimpleResource? = null
)
