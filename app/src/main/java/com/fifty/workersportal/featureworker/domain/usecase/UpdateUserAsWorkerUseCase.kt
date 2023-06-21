package com.fifty.workersportal.featureworker.domain.usecase

import android.net.Uri
import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository
import com.fifty.workersportal.featureworker.data.remote.request.WorkerCategoryRequest
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerResult
import com.fifty.workersportal.featureworker.util.ProfileError
import java.util.Locale

class UpdateUserAsWorkerUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        updateWorkerData: UpdateWorkerData,
        profilePictureUri: Uri?
    ): UpdateWorkerResult {
        val firstNameError = ValidationUtil.validateFirstName(updateWorkerData.firstName)
        val emailError = ValidationUtil.validateEmail(updateWorkerData.email)
        val ageError = ValidationUtil.validateWorkerAge(updateWorkerData.age)
        val bioError = if (updateWorkerData.bio.isBlank()) ProfileError.InvalidBio else null
        val skillsError =
            if (updateWorkerData.categoryList.isEmpty()) ProfileError.NoSkillSelected else null
        val primarySkillError =
            if (updateWorkerData.primarySkill == null ||
                !updateWorkerData.categoryList.any { it == updateWorkerData.primarySkill }
            ) {
                ProfileError.NoPrimarySkillSelected
            } else null

        if (firstNameError != null || emailError != null ||
            ageError != null || bioError != null || skillsError != null || primarySkillError != null
        ) {
            return UpdateWorkerResult(
                firstNameError = firstNameError,
                emailError = emailError,
                bioError = bioError,
                ageError = ageError,
                skillsError = skillsError,
                primarySkillError = primarySkillError
            )
        } else {
            val result = profileRepository.updateProfileForWorker(
                UpdateProfileForWorkerRequest(
                    userId = updateWorkerData.userId,
                    openToWork = updateWorkerData.openToWork,
                    firstName = updateWorkerData.firstName,
                    lastName = updateWorkerData.lastName,
                    email = updateWorkerData.email,
                    bio = updateWorkerData.bio,
                    gender = updateWorkerData.gender.lowercase(Locale.ROOT),
                    age = updateWorkerData.age,
                    categoryList = updateWorkerData.categoryList.map { workerCategory ->
                        WorkerCategoryRequest(
                            id = workerCategory.id,
                            hourlyWage = workerCategory.hourlyWage.toFloat(),
                            dailyWage = workerCategory.dailyWage.toFloat()
                        )
                    },
                    primaryCategory = updateWorkerData.primarySkill?.id ?: ""
                ),
                profilePictureUri = profilePictureUri
            ).data

            result?.let { profile ->
                return UpdateWorkerResult(result = Resource.Success(data = profile))
            } ?: return UpdateWorkerResult(result = Resource.Error(UiText.unknownError()))
        }
    }
}