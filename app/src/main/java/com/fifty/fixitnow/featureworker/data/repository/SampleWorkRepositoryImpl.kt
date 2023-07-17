package com.fifty.fixitnow.featureworker.data.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.fixitnow.featureworker.data.paging.SampleWorkSource
import com.fifty.fixitnow.featureworker.data.remote.WorkerApiService
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.repository.SampleWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
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

    override fun getSampleWorksForUser(userId: String): Flow<PagingData<SampleWork>> {
        val pagingConfig = PagingConfig(pageSize = Constants.DEFAULT_PAGINATION_SIZE)

        return Pager(pagingConfig) {
            SampleWorkSource(api, userId)
        }.flow
    }

    override suspend fun getSampleWork(sampleWorkId: String): Resource<SampleWork> {
        return try {
            val response = api.getSampleWork(sampleWorkId)
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

    override suspend fun deleteSampleWork(sampleWorkId: String): SimpleResource {
        return try {
            val response = api.deleteSampleWork(sampleWorkId)
            if (response.successful) {
                Resource.Success(Unit)
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