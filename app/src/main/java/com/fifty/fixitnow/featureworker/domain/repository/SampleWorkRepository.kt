package com.fifty.fixitnow.featureworker.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.SimpleResource
import com.fifty.fixitnow.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.fixitnow.featureworker.domain.model.SampleWork
import kotlinx.coroutines.flow.Flow

interface SampleWorkRepository {

    suspend fun postSampleWork(
        sampleWorkRequest: SampleWorkRequest,
        imageUri: Uri?
    ): Resource<SampleWork>

    fun getSampleWorksForUser(userId: String): Flow<PagingData<SampleWork>>

    suspend fun getSampleWork(sampleWorkId: String): Resource<SampleWork>

    suspend fun deleteSampleWork(sampleWorkId: String): SimpleResource
}