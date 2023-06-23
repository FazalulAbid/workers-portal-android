package com.fifty.workersportal.featureworker.domain.usecase

import android.net.Uri
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.workersportal.featureworker.domain.model.PostSampleWorkResult
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository
import com.fifty.workersportal.featureworker.util.SampleWorkError

class PostSampleWorkUseCase(
    private val repository: SampleWorkRepository
) {

    suspend operator fun invoke(sampleWork: SampleWork, imageUri: Uri?): PostSampleWorkResult {
        val imageError = if (imageUri == null) SampleWorkError.NoImageError else null
        if (imageError != null) {
            return PostSampleWorkResult(
                imageError = imageError
            )
        } else {
            val result = repository.postSampleWork(
                sampleWorkRequest = SampleWorkRequest(
                    title = sampleWork.title,
                    description = sampleWork.description
                ),
                imageUri = imageUri
            ).data

            result?.let {
                return PostSampleWorkResult(result = Resource.Success(data = it))
            } ?: return PostSampleWorkResult(result = Resource.Error(UiText.unknownError()))
        }
    }
}