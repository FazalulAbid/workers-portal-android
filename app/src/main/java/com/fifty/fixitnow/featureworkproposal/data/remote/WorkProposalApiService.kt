package com.fifty.fixitnow.featureworkproposal.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.featureworkproposal.data.remote.dto.WorkProposalDto
import com.fifty.fixitnow.featureworkproposal.data.remote.request.SendWorkProposalRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorkProposalApiService {

    @POST("proposal/add-proposal")
    suspend fun sendWorkProposal(
        @Body workProposalRequest: SendWorkProposalRequest
    ): Response<BasicApiResponse<WorkProposalDto>>

    @GET("proposal/get-proposals")
    suspend fun getWorkProposalsForWorker(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<Any>
}