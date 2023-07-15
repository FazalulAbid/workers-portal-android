package com.fifty.workersportal.featureworker.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureuser.data.remote.FavouriteUpdateRequest
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import java.io.IOException

class WorkerRepositoryImpl(
    private val api: WorkerApiService
) : WorkerRepository {

    val worker = Worker(
        workerId = "1234567890",
        firstName = "John",
        lastName = "Doe",
        isVerified = true,
        gender = "Male",
        bio = "I am an experienced worker with various skills.",
        categoryList = listOf(
            WorkerCategory(
                id = "1",
                title = "Plumber",
                skill = "Plumbing",
                imageUrl = "https://example.com/plumber.jpg",
                dailyMinWage = 50.0f,
                hourlyMinWage = 10.0f,
                dailyWage = "50 USD",
                hourlyWage = "10 USD/h"
            ),
            WorkerCategory(
                id = "2",
                title = "Electrician",
                skill = "Electrical Work",
                imageUrl = "https://example.com/electrician.jpg",
                dailyMinWage = 60.0f,
                hourlyMinWage = 12.0f,
                dailyWage = "60 USD",
                hourlyWage = "12 USD/h"
            )
        ),
        openToWork = true,
        userId = "9876543210",
        profileImageUrl = "https://example.com/profile.jpg",
        ratingAverage = 4.5f,
        ratingCount = 100,
        localAddress = LocalAddress(
            id = "abcd1234",
            title = "Home",
            completeAddress = "123 Main Street",
            floor = "2nd Floor",
            landmark = "Central Park",
            place = "New York",
            subLocality = "Manhattan",
            city = "New York City",
            state = "New York",
            country = "USA",
            pin = "12345",
            location = listOf(40.7128, -74.0060)
        ),
        isFavourite = false,
        primaryCategoryId = "1",
        distance = 5.3
    )

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
                Resource.Success(data = listOf(worker, worker, worker, worker))
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

    override suspend fun getWorkerDetails(workerId: String): Resource<Worker> {
        return try {
            val response = api.getWorkerDetails(workerId)
            if (response.successful) {
                Resource.Success(data = worker)
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