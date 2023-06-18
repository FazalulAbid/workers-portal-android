package com.fifty.workersportal.featureuser.presentation.userprofile

sealed class UserProfileEvent {
    object Logout : UserProfileEvent()
}
