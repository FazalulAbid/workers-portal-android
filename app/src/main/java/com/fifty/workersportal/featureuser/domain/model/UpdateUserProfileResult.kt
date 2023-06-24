package com.fifty.workersportal.featureuser.domain.model

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.util.ProfileError

data class UpdateUserProfileResult(
    val firstNameError: ProfileError? = null,
    val emailError: ProfileError? = null,
    val ageError: ProfileError? = null,
    val unknownError: ProfileError? = null,
    val result: SimpleResource? = null
)
