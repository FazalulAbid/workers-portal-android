package com.fifty.workersportal.featureuser.data.repository

import android.util.Log
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
            title = "Banner 1",
            description = "Description 1",
            imageUrl = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg",
            deeplinkUrl = "https://example.com/banner1"
        ),
        Banner(
            title = "Banner 2",
            description = "Description 2",
            imageUrl = "https://cdn.pixabay.com/photo/2012/08/27/14/19/mountains-55067_640.png",
            deeplinkUrl = "https://example.com/banner2"
        ),
        Banner(
            title = "Banner 3",
            description = "Description 3",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAH_kSbGdhiSQ4tRF1k1h5eFlNFBHux0RHlt94xRxsxw&usqp=CAU&ec=48600112",
            deeplinkUrl = "https://example.com/banner3"
        ),
        Banner(
            title = "Banner 4",
            description = "Description 4",
            imageUrl = "https://cdn11.bigcommerce.com/s-x49po/images/stencil/1280x1280/products/65812/94935/1614072241732_D13B0BF3-195F-4075-AC5B-2EC147D49977__94595.1614166156.jpg?c=2",
            deeplinkUrl = "https://example.com/banner4"
        ),
        Banner(
            title = "Banner 5",
            description = "Description 5",
            imageUrl = "https://mediamodifier.com/blog/wp-content/uploads/2020/04/online-banner-maker-templates.jpg",
            deeplinkUrl = "https://example.com/banner5"
        )
    )

    override suspend fun getBanners(): Resource<List<Banner>> {
        return try {
            val response = api.getUserDashboardBanners()
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toBanner() })
            } else {
                response.message?.let { message ->
                    Resource.Error(UiText.DynamicString(message))
                } ?: Resource.Error(UiText.unknownError())
            }
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