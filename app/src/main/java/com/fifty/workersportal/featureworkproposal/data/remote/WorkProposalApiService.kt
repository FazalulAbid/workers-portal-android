package com.fifty.workersportal.featureworkproposal.data.remote

import com.fifty.workersportal.core.data.dto.response.BasicApiResponse
import com.fifty.workersportal.featureworker.data.remote.request.ReviewAndRatingRequest
import com.fifty.workersportal.featureworker.domain.model.ReviewAndRating
import com.fifty.workersportal.featureworkproposal.data.remote.dto.WorkProposalDto
import com.fifty.workersportal.featureworkproposal.data.remote.request.SendWorkProposalRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkProposalApiService {

    @POST("proposal/add-proposal")
    suspend fun sendWorkProposal(
        @Body workProposalRequest: SendWorkProposalRequest
    ): Response<BasicApiResponse<WorkProposalDto>>
}