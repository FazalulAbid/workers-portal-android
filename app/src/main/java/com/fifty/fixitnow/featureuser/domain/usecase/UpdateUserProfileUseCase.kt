package com.fifty.fixitnow.featureuser.domain.usecase

import android.net.Uri
import com.fifty.fixitnow.core.domain.util.ValidationUtil
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureuser.domain.model.UpdateUserProfileData
import com.fifty.fixitnow.featureuser.domain.model.UpdateUserProfileResult
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository
import java.util.Locale

class UpdateUserProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(
        updateUserProfileData: UpdateUserProfileData,
        profilePictureUri: Uri?
    ): UpdateUserProfileResult {
        val firstNameError = ValidationUtil.validateFirstName(updateUserProfileData.firstName)
        val emailError = ValidationUtil.validateEmail(updateUserProfileData.email)
        val ageError = ValidationUtil.validateWorkerAge(updateUserProfileData.age)

        if (firstNameError != null || emailError != null || ageError != null) {
            return UpdateUserProfileResult(
                firstNameError = firstNameError,
                emailError = emailError,
                ageError = ageError,
            )
        } else {
            val result = profileRepository.updateUserProfile(
                UpdateUserProfileData(
                    firstName = updateUserProfileData.firstName,
                    lastName = updateUserProfileData.lastName,
                    email = updateUserProfileData.email,
                    gender = updateUserProfileData.gender.lowercase(Locale.ROOT),
                    age = updateUserProfileData.age,
                ),
                profilePictureUri = profilePictureUri
            ).data

            result?.let {
                return UpdateUserProfileResult(result = Resource.Success(Unit))
            } ?: return UpdateUserProfileResult(result = Resource.Error(UiText.unknownError()))
        }
    }
}