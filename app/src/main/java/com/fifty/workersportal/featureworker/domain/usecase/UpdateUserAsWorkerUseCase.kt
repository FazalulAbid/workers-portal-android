package com.fifty.workersportal.featureworker.domain.usecase

import android.util.Log
import com.fifty.workersportal.core.domain.model.UserSession
import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository
import com.fifty.workersportal.featureworker.data.remote.request.WorkerCategoryRequest
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerResult
import com.fifty.workersportal.featureworker.util.WorkerError

class UpdateUserAsWorkerUseCase(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(updateWorkerData: UpdateWorkerData): UpdateWorkerResult {
        Log.d("Hello", "invoke: UpdateWorkerUseCase worked")
        val firstNameError = ValidationUtil.validateFirstName(updateWorkerData.firstName)
        val emailError = ValidationUtil.validateEmail(updateWorkerData.email)
        val ageError = ValidationUtil.validateWorkerAge(updateWorkerData.age)
        val bioError = if (updateWorkerData.bio.isBlank()) WorkerError.InvalidBio else null
        val skillsError =
            if (updateWorkerData.categoryList.isEmpty()) WorkerError.NoSkillSelected else null
        val primarySkillError =
            if (updateWorkerData.primarySkill == null ||
                !updateWorkerData.categoryList.any { it == updateWorkerData.primarySkill }
            ) {
                WorkerError.NoPrimarySkillSelected
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
        }

        val result = profileRepository.updateProfileForWorker(
            UpdateProfileForWorkerRequest(
                openToWork = updateWorkerData.openToWork,
                firstName = updateWorkerData.firstName,
                lastName = updateWorkerData.lastName,
                email = updateWorkerData.email,
                bio = updateWorkerData.bio,
                gender = updateWorkerData.gender,
                age = updateWorkerData.age,
                categoryList = updateWorkerData.categoryList.map { workerCategory ->
                    WorkerCategoryRequest(
                        id = workerCategory.id,
                        hourlyWage = workerCategory.hourlyWage.toFloat(),
                        dailyWage = workerCategory.dailyWage.toFloat()
                    )
                }
            )
        ).data

        result?.let { profile ->
            sessionRepository.saveUserSession(
                UserSession(
                    id = profile.id,
                    firstName = profile.firstName,
                    lastName = profile.lastName,
                    isWorker = profile.isWorker
                )
            )
            return UpdateWorkerResult(result = Resource.Success(Unit))
        } ?: return UpdateWorkerResult(result = Resource.Error(UiText.unknownError()))
    }
}