package com.fifty.workersportal.featureworker.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureuser.data.remote.FavouriteUpdateRequest
import com.fifty.workersportal.featureworker.data.paging.CategorySource
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.remote.request.GetWorkerCategoriesRequest
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class WorkerRepositoryImpl(
    private val api: WorkerApiService
) : WorkerRepository {

    override fun getSearchedCategoriesPaged(searchQuery: String): Flow<PagingData<Category>> {
        val pagingConfig = PagingConfig(pageSize = Constants.DEFAULT_PAGINATION_SIZE)

        return Pager(pagingConfig) {
            CategorySource(api, searchQuery)
        }.flow
    }

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

    override suspend fun toggleFavouriteWorker(
        userId: String,
        isFavourite: Boolean
    ): SimpleResource {
        return try {
            val response = if (isFavourite) {
                api.removeWorkerFromFavourites(
                    userId = userId
                )
            } else {
                api.addWorkerToFavourites(
                    FavouriteUpdateRequest(userId)
                )
            }
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

    override suspend fun getSearchedSortedAndFilteredWorkers(
        query: String,
        page: Int,
        pageSize: Int,
        categoryId: String?
    ): Resource<List<Worker>> {
        return try {
            val response = api.getSearchedSortedAndFilteredWorkers(
                query = query,
                page = page,
                pageSize = pageSize,
                category = categoryId
            )
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toWorker() })
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_could_not_reach_server)
            )
        } catch (e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}