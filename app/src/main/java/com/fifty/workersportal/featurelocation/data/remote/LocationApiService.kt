package com.fifty.workersportal.featurelocation.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureuser.data.remote.dto.ProfileDto
import com.fifty.workersportal.featureworker.data.remote.request.UpdateProfileForWorkerRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationApiService {

    @POST("locatin/asfd")
    suspend fun saveAddress(
        @Body localAddress: LocalAddress
    ): BasicApiResponse<Unit>
}