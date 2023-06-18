package com.fifty.workersportal.featureuser.presentation.userprofile

import com.fifty.workersportal.featureuser.domain.model.Profile

data class UserProfileState(
    val isLoading: Boolean = false,
    val profile: Profile? = null
)
