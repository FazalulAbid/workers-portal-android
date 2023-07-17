package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureworker.domain.repository.SampleWorkRepository

class DeleteSampleWorkUseCase(
    private val repository: SampleWorkRepository
) {

    suspend operator fun invoke(sampleWorkId: String): SimpleResource =
        repository.deleteSampleWork(sampleWorkId)
}