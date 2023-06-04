package com.fifty.workersportal.core.domain.usecase

import android.util.Log
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first

class GetOwnUserIdUseCase(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(): String {
        val user = repository.getUserSession()
        return user.id
    }
}