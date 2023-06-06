package com.fifty.workersportal.featureworker.data.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.data.remote.request.UpdateWorkerRequest
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository

class WorkerRepositoryImpl(

) : WorkerRepository {
    override suspend fun updateWorker(updateWorkerRequest: UpdateWorkerRequest): SimpleResource {
        return Resource.Success(Unit)
    }
}