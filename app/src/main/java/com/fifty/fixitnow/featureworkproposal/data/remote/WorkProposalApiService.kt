package com.fifty.fixitnow.featureworkproposal.data.remote

import com.fifty.fixitnow.core.data.dto.response.BasicApiResponse
import com.fifty.fixitnow.featureworker.data.remote.dto.WorkerDto
import com.fifty.fixitnow.featureworkproposal.data.remote.dto.WorkProposalDto
import com.fifty.fixitnow.featureworkproposal.data.remote.dto.WorkProposalForWorkerDto
import com.fifty.fixitnow.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalForWorker
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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
    ): BasicApiResponse<List<WorkProposalForWorkerDto>>

    @PATCH("proposal/accept-proposal")
    suspend fun acceptWorkProposal(
        @Query("proposalId") workProposalId: String
    ): BasicApiResponse<String>

    @PATCH("proposal/reject-proposal")
    suspend fun rejectWorkProposal(
        @Query("proposalId") workProposalId: String
    ): BasicApiResponse<String>

    @GET("proposal/get-report")
    suspend fun getWorkHistoryReport(
        @Query("fromDate") fromDate: Long,
        @Query("toDate") toDate: Long,
        @Query("workHistory") isWorkHistoryNeeded: Boolean,
        @Query("hiringHistory") isHiringHistoryNeeded: Boolean,
        @Query("pendingWorks") isPendingWorksNeeded: Boolean,
        @Query("completedWorks") isCompletedWorksNeeded: Boolean,
        @Query("cancelledWorks") isCancelledWorksNeeded: Boolean,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<Any>
}