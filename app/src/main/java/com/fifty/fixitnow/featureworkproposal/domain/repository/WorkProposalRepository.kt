package com.fifty.fixitnow.featureworkproposal.domain.repository

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworkproposal.data.remote.request.SendWorkProposalRequest
import com.fifty.fixitnow.featureworkproposal.domain.model.WorkProposal

interface WorkProposalRepository {

    suspend fun sendWorkProposal(workProposalRequest: SendWorkProposalRequest): Resource<WorkProposal>

    suspend fun getWorkProposalsForWorker(page: Int): SimpleResource
}