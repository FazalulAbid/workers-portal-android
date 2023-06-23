package com.fifty.workersportal.featureworker.domain.repository

import android.net.Uri
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureuser.data.remote.request.SampleWorkRequest
import com.fifty.workersportal.featureworker.domain.model.SampleWork

interface SampleWorkRepository {

    suspend fun postSampleWork(sampleWorkRequest: SampleWorkRequest, imageUri: Uri?): Resource<SampleWork>
}