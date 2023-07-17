package com.fifty.fixitnow.featureuser.presentation.edituserprofile

import com.fifty.fixitnow.featureuser.domain.model.UserProfile

data class UpdateUserProfileState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val loadingText: Int? = null
)
