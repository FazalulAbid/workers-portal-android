package com.fifty.fixitnow.featureauth.domain.usecase

import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.featureauth.domain.model.OtpVerification
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository
import com.fifty.fixitnow.featureuser.domain.model.UserProfile

class GoogleSignInUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(googleToken: String): Resource<OtpVerification> =
        repository.googleSignIn(googleToken)
}