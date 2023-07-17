package com.fifty.fixitnow.featureuser.presentation.userprofile

sealed class UserProfileEvent {
    object UserProfileUpdated: UserProfileEvent()
    object Logout : UserProfileEvent()
}
