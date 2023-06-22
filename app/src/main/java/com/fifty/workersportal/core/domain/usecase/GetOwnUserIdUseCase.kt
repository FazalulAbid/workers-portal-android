package com.fifty.workersportal.core.domain.usecase

import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first

class GetOwnUserIdUseCase(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(): String =
        repository.getOwnUserId().first() ?: ""
}