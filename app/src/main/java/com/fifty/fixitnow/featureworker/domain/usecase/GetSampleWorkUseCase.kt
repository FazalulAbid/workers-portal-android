package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.repository.SampleWorkRepository

class GetSampleWorkUseCase(
    private val repository: SampleWorkRepository
) {

    suspend operator fun invoke(sampleWorkId: String): Resource<SampleWork> =
        repository.getSampleWork(sampleWorkId)
}