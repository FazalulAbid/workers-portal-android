package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.featureauth.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingStateUseCase(
    private val repository: OnBoardingRepository
) {

    operator fun invoke() = repository.readOnBoardingState()
}