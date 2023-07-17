package com.fifty.fixitnow.featureuser.domain.model

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.util.ProfileError

data class UpdateUserProfileResult(
    val firstNameError: ProfileError? = null,
    val emailError: ProfileError? = null,
    val ageError: ProfileError? = null,
    val unknownError: ProfileError? = null,
    val result: SimpleResource? = null
)
