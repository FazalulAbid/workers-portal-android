package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerValidationResult
import com.fifty.workersportal.featureworker.util.ProfileError

class ValidateUserAsWorkerUseCase {

    operator fun invoke(updateWorkerData: UpdateWorkerData): UpdateWorkerValidationResult {
        val firstNameError = ValidationUtil.validateFirstName(updateWorkerData.firstName)
        val emailError = ValidationUtil.validateEmail(updateWorkerData.email)
        val ageError = ValidationUtil.validateWorkerAge(updateWorkerData.age)
        val bioError = if (updateWorkerData.bio.isBlank()) ProfileError.InvalidBio else null
        val skillsError =
            if (updateWorkerData.categoryList.isEmpty()) ProfileError.NoSkillSelected else null
        val primarySkillError =
            if (updateWorkerData.primarySkillId == null ||
                !updateWorkerData.categoryList.any { it.id == updateWorkerData.primarySkillId }
            ) {
                ProfileError.NoPrimarySkillSelected
            } else null

        return if (firstNameError != null || emailError != null ||
            ageError != null || bioError != null || skillsError != null || primarySkillError != null
        ) {
            UpdateWorkerValidationResult(
                firstNameError = firstNameError,
                emailError = emailError,
                bioError = bioError,
                ageError = ageError,
                skillsError = skillsError,
                primarySkillError = primarySkillError
            )
        } else {
            UpdateWorkerValidationResult(result = updateWorkerData)
        }
    }
}