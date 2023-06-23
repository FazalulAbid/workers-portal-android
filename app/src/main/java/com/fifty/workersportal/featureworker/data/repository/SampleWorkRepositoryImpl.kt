package com.fifty.workersportal.featureworker.data.repository

import android.net.Uri
import androidx.core.net.toFile
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

class SampleWorkRepositoryImpl(
    private val api: WorkerApiService,
    private val gson: Gson
) : SampleWorkRepository {

    override suspend fun postSampleWork(
        sampleWorkRequest: SampleWorkRequest,
        imageUri: Uri?
    ): Resource<SampleWork> {
        val postImageFile = imageUri?.toFile()
        return try {
            val response = api.postSampleWork(
                imageUrl = postImageFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "sampleWorkImage",
                            postImageFile.name,
                            postImageFile.asRequestBody()
                        )
                },
                sampleWorkRequest = MultipartBody.Part
                    .createFormData(
                        "data",
                        gson.toJson(sampleWorkRequest)
                    )
            )
            if (response.successful) {
                Resource.Success(data = response.data?.toSampleWork())
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_could_not_reach_server
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.oops_something_went_wrong
                )
            )
        }
    }
}