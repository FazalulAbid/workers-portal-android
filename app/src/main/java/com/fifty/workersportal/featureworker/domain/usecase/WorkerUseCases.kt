package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.core.domain.usecase.GetUserProfileDetailsUseCase

data class WorkerUseCases(
    val updateUserAsWorker: UpdateUserAsWorkerUseCase,
    val setSkillSelected: SetSkillSelectedUseCase,
    val getUserProfileDetails: GetUserProfileDetailsUseCase,
    val getCategories: GetCategoriesUseCase
)
