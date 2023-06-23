package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository

class DeleteSampleWorkUseCase(
    private val repository: SampleWorkRepository
) {

    suspend operator fun invoke(sampleWorkId: String): SimpleResource =
        repository.deleteSampleWork(sampleWorkId)
}