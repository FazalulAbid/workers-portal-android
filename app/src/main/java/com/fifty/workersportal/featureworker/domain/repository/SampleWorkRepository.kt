package com.fifty.workersportal.featureworker.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.SimpleResource
import com.fifty.workersportal.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.workersportal.featureworker.data.remote.dto.SampleWorkDto
import com.fifty.workersportal.featureworker.domain.model.SampleWork
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