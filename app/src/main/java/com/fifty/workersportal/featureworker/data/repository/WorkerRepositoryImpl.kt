package com.fifty.workersportal.featureworker.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import java.io.IOException

class WorkerRepositoryImpl(
    private val api: WorkerApiService
) : WorkerRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return try {
            val response = api.getCategories()
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toCategory() })
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

    override suspend fun getSuggestedCategories(): Resource<List<Category>> {
        return try {
            val response = api.getSuggestedCategories()
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toCategory() })
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

    override suspend fun toggleOpenToWork(value: Boolean): SimpleResource {
        return try {
            val response = if (value) {
                api.openToWorkOn()
            } else api.openToWorkOff()
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