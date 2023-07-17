package com.fifty.fixitnow.featureworker.domain.usecase

import android.net.Uri
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository
import com.fifty.fixitnow.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import com.fifty.fixitnow.featureworker.data.remote.request.WorkerCategoryRequest
import com.fifty.fixitnow.featureworker.domain.model.UpdateWorkerData
import java.util.Locale

class UpdateUserAsWorkerUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        updateWorkerData: UpdateWorkerData,
        profilePictureUri: Uri?,
        identityPictureUri: Uri?
    ): SimpleResource {
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
                primaryCategory = updateWorkerData.primarySkillId
                    ?: updateWorkerData.categoryList.first().id
            ),
            profilePictureUri = profilePictureUri,
            identityPictureUri = identityPictureUri
        ).data

        result?.let {
            return Resource.Success(Unit)
        } ?: return Resource.Error(UiText.unknownError())
    }
}