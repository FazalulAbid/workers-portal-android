package com.fifty.fixitnow.featureuser.presentation.userprofile

import com.fifty.fixitnow.featureuser.domain.model.UserProfile

data class UserProfileState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null
)
