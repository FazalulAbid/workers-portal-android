package com.fifty.fixitnow.featureworker.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import com.fifty.fixitnow.featureworker.domain.repository.SampleWorkRepository
import kotlinx.coroutines.flow.Flow

class GetSampleWorksUseCase(
    private val repository: SampleWorkRepository
) {

    operator fun invoke(userId: String): Flow<PagingData<SampleWork>> {
        return repository.getSampleWorksForUser(userId)
    }
}

