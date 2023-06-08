package com.fifty.workersportal.featureworker.domain.repository

import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureworker.domain.model.Category
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

interface WorkerRepository {

    suspend fun getCategories(): Resource<List<Category>>
}