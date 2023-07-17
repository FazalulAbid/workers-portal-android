package com.fifty.fixitnow.featureuser.presentation.edituserprofile

import android.net.Uri

sealed class UpdateUserProfileEvent {
    data class EnterFirstName(val firstName: String) : UpdateUserProfileEvent()
    data class EnterLastName(val lastName: String) : UpdateUserProfileEvent()
    data class EnterEmail(val email: String) : UpdateUserProfileEvent()
    data class GenderSelectedChanged(val gender: String) : UpdateUserProfileEvent()
    data class EnterAge(val age: String) : UpdateUserProfileEvent()
    data class CropProfilePicture(val uri: Uri?) : UpdateUserProfileEvent()
    object UpdateUserProfile : UpdateUserProfileEvent()
}
