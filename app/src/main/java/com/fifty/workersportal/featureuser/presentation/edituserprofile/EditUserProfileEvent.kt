package com.fifty.workersportal.featureuser.presentation.edituserprofile

import android.net.Uri

sealed class EditUserProfileEvent {
    data class EnterFirstName(val firstName: String) : EditUserProfileEvent()
    data class EnterLastName(val lastName: String) : EditUserProfileEvent()
    data class EnterEmail(val email: String) : EditUserProfileEvent()
    data class GenderSelectedChanged(val gender: String) : EditUserProfileEvent()
    data class EnterAge(val age: String) : EditUserProfileEvent()
    data class CropProfilePicture(val uri: Uri?) : EditUserProfileEvent()
    object UpdateUserProfile : EditUserProfileEvent()
}
