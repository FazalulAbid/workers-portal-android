package com.fifty.workersportal.featureworker.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.fifty.workersportal.featureworker.domain.model.SampleWork
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository
import kotlinx.coroutines.flow.Flow

class GetSampleWorksUseCase(
    private val repository: SampleWorkRepository
) {

    operator fun invoke(userId: String): Flow<PagingData<SampleWork>> {
        Log.d("Hello", "invoke: use case worked")
        return repository.getSampleWorksForUser(userId)
    }
}

