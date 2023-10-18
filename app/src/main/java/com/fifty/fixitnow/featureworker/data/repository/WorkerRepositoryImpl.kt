package com.fifty.fixitnow.featureworker.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureuser.data.remote.FavouriteUpdateRequest
import com.fifty.fixitnow.featureworker.data.remote.WorkerApiService
import com.fifty.fixitnow.featureworker.domain.model.Category
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository
import java.io.IOException

class WorkerRepositoryImpl(
    private val api: WorkerApiService
) : WorkerRepository {

    override suspend fun getSearchedCategoriesPaged(
        page: Int,
        searchQuery: String
    ): Resource<List<Category>> {
        return try {
            val response = api.searchCategory(
                page = page,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE,
                searchQuery = searchQuery
            )
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

    override suspend fun getFavourites(page: Int): Resource<List<Worker>> {
        return try {
            val response = api.getFavourites(
                page = page,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE
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
        categoryId: String?,
        rating4PlusFilter: Boolean?,
        previouslyHiredFilter: Boolean?,
        sortFlag: Int?,
        availabilityCheckDate: Long?,
        isFullDay: Boolean?,
        isBeforeNoon: Boolean?
    ): Resource<List<Worker>> {
        return try {
            Log.d("Hello", "getSearchedSortedAndFilteredWorkers: Hit")
            val response = api.getSearchedSortedAndFilteredWorkers(
                query = query,
                page = page,
                pageSize = pageSize,
                category = categoryId,
                sortFlag = sortFlag,
                rating4PlusFilter = rating4PlusFilter,
                previouslyHiredFilter = previouslyHiredFilter,
                date = availabilityCheckDate,
                isFullDay = isFullDay,
                isBeforeNoon = isBeforeNoon
            )
            Log.d("Hello", "getSearchedSortedAndFilteredWorkers: ${response.data}")
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

    override suspend fun getWorkerDetails(workerId: String): Resource<Worker> {
        return try {
            val response = api.getWorkerDetails(workerId)
            if (response.successful) {
                Resource.Success(data = response.data?.toWorker())
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