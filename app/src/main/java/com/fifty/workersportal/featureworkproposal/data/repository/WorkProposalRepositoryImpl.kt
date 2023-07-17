package com.fifty.workersportal.featureworkproposal.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.util.HttpError
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworkproposal.data.remote.WorkProposalApiService
import com.fifty.workersportal.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.workersportal.featureworkproposal.domain.model.WorkProposal
import com.fifty.workersportal.featureworkproposal.domain.repository.WorkProposalRepository
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
}