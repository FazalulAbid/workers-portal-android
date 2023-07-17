package com.fifty.fixitnow.core.domain.usecase

import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first

class GetOwnUserIdUseCase(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(): String =
        repository.getOwnUserId().first() ?: ""
}