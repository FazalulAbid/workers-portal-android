package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.featureauth.domain.repository.OnBoardingRepository

class SaveOnBoardingStateUseCase(
    private val repository: OnBoardingRepository
) {

    suspend operator fun invoke(completed: Boolean) =
        repository.saveOnBoardingState(completed)
}