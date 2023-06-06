package com.fifty.workersportal.featureworker.domain.repository

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.data.remote.request.UpdateWorkerRequest

interface WorkerRepository {

    suspend fun updateWorker(updateWorkerRequest: UpdateWorkerRequest): SimpleResource
}