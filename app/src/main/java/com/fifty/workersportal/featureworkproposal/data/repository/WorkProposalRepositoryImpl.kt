package com.fifty.workersportal.featureworkproposal.data.repository

import coil.network.HttpException
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
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
            if (response.successful) {
                Resource.Success(data = response.data?.toWorkProposal())
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