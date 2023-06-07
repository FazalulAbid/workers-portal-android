package com.fifty.workersportal.featureworker.domain.usecase

import android.util.Log
import com.fifty.workersportal.core.domain.util.ValidationUtil
import com.fifty.workersportal.featureworker.data.remote.dto.WorkerCategoryDto
import com.fifty.workersportal.featureworker.data.remote.request.UpdateWorkerRequest
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerData
import com.fifty.workersportal.featureworker.domain.model.UpdateWorkerResult
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.fifty.workersportal.featureworker.util.WorkerError

class UpdateWorkerUseCase(
    private val repository: WorkerRepository
) {
    suspend operator fun invoke(updateWorkerData: UpdateWorkerData): UpdateWorkerResult {
        Log.d("Hello", "invoke: UpdateWorkerUseCase worked")
        val firstNameError = ValidationUtil.validateFirstName(updateWorkerData.firstName)
        val emailError = ValidationUtil.validateEmail(updateWorkerData.email)
        val ageError = ValidationUtil.validateWorkerAge(updateWorkerData.age)
        val bioError = if (updateWorkerData.bio.isBlank()) WorkerError.InvalidBio else null
        val skillsError =
            if (updateWorkerData.categoryList.isEmpty()) WorkerError.NoSkillSelected else null

        if (firstNameError != null || emailError != null ||
            ageError != null || bioError != null || skillsError != null
        ) {
            return UpdateWorkerResult(
                firstNameError = firstNameError,
                emailError = emailError,
                bioError = bioError,
                ageError = ageError,
                skillsError = skillsError
            )
        }

        val result = repository.updateWorker(
            UpdateWorkerRequest(
                openToWork = updateWorkerData.openToWork,
                firstName = updateWorkerData.firstName,
                lastName = updateWorkerData.lastName,
                email = updateWorkerData.email,
                bio = updateWorkerData.bio,
                gender = updateWorkerData.gender,
                age = updateWorkerData.age,
                categoryList = updateWorkerData.categoryList.map { workerCategory ->
                    WorkerCategoryDto(
                        id = workerCategory.id,
                        hourlyWage = workerCategory.hourlyWage.toFloat(),
                        dailyWage = workerCategory.dailyWage.toFloat()
                    )
                }
            )
        )

        return UpdateWorkerResult(
            result = result
        )
    }
}