package com.fifty.workersportal.featureuser.presentation.userprofile

sealed class UserProfileEvent {
    object UserProfileUpdated: UserProfileEvent()
    object Logout : UserProfileEvent()
}
