package com.fifty.fixitnow.featureworkproposal.data.repository

import android.util.Log
import coil.network.HttpException
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.util.HttpError
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworkproposal.data.remote.WorkProposalApiService
import com.fifty.fixitnow.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposalForWorker
import com.fifty.fixitnow.featureworkproposal.domain.repository.WorkProposalRepository
import java.io.IOException

class WorkProposalRepositoryImpl(
    private val api: WorkProposalApiService
) : WorkProposalRepository {

    override suspend fun sendWorkProposal(workProposalRequest: SendWorkProposalRequest): Resource<WorkProposal> {
        return try {
            val response = api.sendWorkProposal(workProposalRequest)
            if (response.code() == 409) {
                Resource.Error(
                    errorCode = HttpError.WORKER_DATE_CONFLICT,
                    uiText = UiText.StringResource(R.string.work_proposals)
                )
            } else {
                val responseData = response.body()
                if (responseData?.successful == true) {
                    Log.d("Hello", "sendWorkProposal: ${response.body().toString()}")
                    Resource.Success(data = responseData.data?.toWorkProposal())
                } else {
                    responseData?.message?.let { message ->
                        Resource.Error(UiText.DynamicString(message))
                    } ?: Resource.Error(UiText.unknownError())
                }
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

    override suspend fun getWorkProposalsForWorker(page: Int): Resource<List<WorkProposalForWorker>> {
        return try {
            val response = api.getWorkProposalsForWorker(
                page = page,
                pageSize = Constants.DEFAULT_PAGINATION_SIZE
            )
            if (response.successful) {
                Resource.Success(data = response.data?.map { it.toWorkProposalForWorker() })
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

    override suspend fun acceptOrRejectWorkProposal(
        workProposalId: String,
        isAcceptProposal: Boolean
    ): SimpleResource {
        return try {
            val response = if (isAcceptProposal) {
                api.acceptWorkProposal(workProposalId)
            } else {
                api.rejectWorkProposal(workProposalId)
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
}