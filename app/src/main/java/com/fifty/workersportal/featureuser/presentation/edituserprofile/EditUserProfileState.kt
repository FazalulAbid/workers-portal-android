package com.fifty.workersportal.featureuser.presentation.edituserprofile

import com.fifty.workersportal.featureuser.domain.model.UserProfile

data class EditUserProfileState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false
)
