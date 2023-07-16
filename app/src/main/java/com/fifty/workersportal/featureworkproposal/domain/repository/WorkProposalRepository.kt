package com.fifty.workersportal.featureworkproposal.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.workersportal.featureworkproposal.domain.model.WorkProposal

interface WorkProposalRepository {

    suspend fun sendWorkProposal(workProposalRequest: SendWorkProposalRequest): Resource<WorkProposal>
}