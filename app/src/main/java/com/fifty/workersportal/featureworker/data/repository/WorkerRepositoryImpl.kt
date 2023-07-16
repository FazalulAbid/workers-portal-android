package com.fifty.workersportal.featureworker.data.repository

import android.util.Log
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
        workerId = "64aac73da1ba9b10fb30d556",
        firstName = "Misbahul",
        lastName = "Haq",
        isVerified = false,
        gender = "male",
        bio = "This is a bio",
        categoryList = listOf(
            WorkerCategory(
                id = "6480a0882017d2a3619674fa",
                title = "Cleaner",
                skill = "Cleaning",
                imageUrl = "https://cdn-icons-png.flaticon.com/512/1670/1670444.png",
                dailyWage = 550.0.toString(),
                hourlyWage = 27.0.toString(),
                dailyMinWage = null,
                hourlyMinWage = null
            ),
            WorkerCategory(
                id = "6480a0b42017d2a3619674fc",
                title = "Electrician",
                skill = "Electrical Work",
                imageUrl = "https://cdn-icons-png.flaticon.com/512/1670/1670444.png",
                dailyWage = 800.0.toString(),
                hourlyWage = 45.0.toString(),
                dailyMinWage = null,
                hourlyMinWage = null
            ),
            WorkerCategory(
                id = "6480a0882017d2a3619674fb",
                title = "Gardener",
                skill = "Gardening",
                imageUrl = "https://cdn-icons-png.flaticon.com/512/1670/1670444.png",
                dailyWage = 600.0.toString(),
                hourlyWage = 30.0.toString(),
                hourlyMinWage = null,
                dailyMinWage = null
            ),
            WorkerCategory(
                id = "6480a0882017d2a3619674fc",
                title = "Security Guard",
                skill = "Security",
                imageUrl = "https://cdn-icons-png.flaticon.com/512/1670/1670444.png",
                dailyWage = 700.0.toString(),
                hourlyWage = 35.0.toString(),
                hourlyMinWage = null,
                dailyMinWage = null
            )
        ),
        openToWork = true,
        userId = "64aac73da1ba9b10fb30d556",
        profileImageUrl = "http://res.cloudinary.com/doazsqomm/image/upload/v1688913787/main/profilePicture/64aac73da1ba9b10fb30d556.png.jpg",
        ratingAverage = 4f,
        ratingCount = 1,
        localAddress = LocalAddress(
            id = "649b04e338ee43f6bca5ba0c",
            title = "Work",
            completeAddress = "Parakkattil House",
            floor = "",
            landmark = "",
            place = "Shastri Nagar",
            subLocality = "Maradu",
            city = "Kochi",
            state = "Kerala",
            country = "India",
            pin = "682304",
            location = listOf(10.7867, 76.6548)
        ),
        isFavourite = true,
        primaryCategoryId = "6480a0882017d2a3619674fa",
        distance = 0.8807849916403581
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
                Log.d("Hello", "getFavourites: $response")
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