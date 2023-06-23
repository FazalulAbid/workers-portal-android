package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository

class GetSampleWorkUseCase(
    private val repository: SampleWorkRepository
) {

    suspend operator fun invoke(sampleWorkId: String): Resource<SampleWork> =
        repository.getSampleWork(sampleWorkId)
}