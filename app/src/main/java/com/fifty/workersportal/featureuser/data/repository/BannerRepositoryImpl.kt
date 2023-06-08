package com.fifty.workersportal.featureuser.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureauth.data.remote.request.SendOtpRequest
import com.fifty.workersportal.featureuser.data.remote.BannerApiService
import com.fifty.workersportal.featureuser.domain.model.Banner
import com.fifty.workersportal.featureuser.domain.repository.BannerRepository
import java.io.IOException

class BannerRepositoryImpl(
    private val api: BannerApiService
) : BannerRepository {

    val banners = listOf(
        Banner(
            id = "1",
            title = "Banner 1",
            description = "Description 1",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner1"
        ),
        Banner(
            id = "2",
            title = "Banner 2",
            description = "Description 2",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner2"
        ),
        Banner(
            id = "3",
            title = "Banner 3",
            description = "Description 3",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner3"
        ),
        Banner(
            id = "4",
            title = "Banner 4",
            description = "Description 4",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner4"
        ),
        Banner(
            id = "5",
            title = "Banner 5",
            description = "Description 5",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner5"
        )
    )

    override suspend fun getBanners(): Resource<List<Banner>> {
        return try {
//            val response = api.getUserDashboardBanners()
//            if (response.successful) {
//                Resource.Success(data = response.data?.map { it.toBanner() })
//            } else {
//                response.message?.let { message ->
//                    Resource.Error(UiText.DynamicString(message))
//                } ?: Resource.Error(UiText.unknownError())
//            }
            Resource.Success(data = banners)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_could_not_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}