package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureworker.domain.model.Worker
import com.fifty.fixitnow.featureworker.domain.repository.WorkerRepository

class GetSearchedSortedAndFilteredWorkersUseCase(
    private val repository: WorkerRepository
) {

    suspend operator fun invoke(
        page: Int,
        query: String,
        categoryId: String?,
        rating4PlusFilter: Boolean? = null,
        previouslyHiredFilter: Boolean? = null,
        isRatingHighToLowSort: Boolean = false,
        isDistanceLowToHighSort: Boolean = false,
        isWageLowToHighSort: Boolean = false,
        isWageHighToLowSort: Boolean = false,
        availabilityCheckDate: Long? = null,
        isFullDay: Boolean? = null,
        isBeforeNoon: Boolean? = null
    ): Resource<List<Worker>> {
        val sortFlag = when {
            isRatingHighToLowSort -> 0
            isDistanceLowToHighSort -> 1
            isWageLowToHighSort -> 2
            isWageHighToLowSort -> 3
            else -> null
        }
        return repository.getSearchedSortedAndFilteredWorkers(
            query = query,
            page = page,
            pageSize = Constants.DEFAULT_PAGINATION_SIZE,
            categoryId = categoryId,
            rating4PlusFilter = rating4PlusFilter,
            previouslyHiredFilter = previouslyHiredFilter,
            sortFlag = sortFlag,
            availabilityCheckDate = availabilityCheckDate,
            isFullDay = isFullDay,
            isBeforeNoon = isBeforeNoon
        )
    }
}