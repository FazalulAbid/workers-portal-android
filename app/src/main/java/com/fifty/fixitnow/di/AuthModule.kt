package com.fifty.fixitnow.di

import android.content.Context
import com.fifty.fixitnow.featureauth.data.remote.AuthApiService
import com.fifty.fixitnow.featureauth.data.remote.AuthenticateApiService
import com.fifty.fixitnow.featureauth.data.repository.AuthRepositoryImpl
import com.fifty.fixitnow.featureauth.data.repository.OnBoardingRepositoryImpl
import com.fifty.fixitnow.featureauth.data.repository.SessionRepositoryImpl
import com.fifty.fixitnow.featureauth.domain.repository.AuthRepository
import com.fifty.fixitnow.featureauth.domain.repository.OnBoardingRepository
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import com.fifty.fixitnow.featureauth.domain.usecase.AuthUseCases
import com.fifty.fixitnow.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.GetAccessTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.GetOtpUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.GoogleSignInUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.LogoutUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.ReadOnBoardingStateUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveAccessTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveOnBoardingStateUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveRefreshTokenUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.SaveUserIdUseCase
import com.fifty.fixitnow.featureauth.domain.usecase.VerifyOtpUseCase
import com.fifty.fixitnow.featureauth.utils.AuthAuthenticator
import com.fifty.fixitnow.featureauth.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthenticateApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): AuthenticateApiService =
        retrofit
            .client(client)
            .build()
            .create(AuthenticateApiService::class.java)

    @Provides
    @Singleton
    fun provideSessionRepository(@ApplicationContext context: Context): SessionRepository =
        SessionRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionRepository: SessionRepository): AuthInterceptor =
        AuthInterceptor(sessionRepository)

    @Provides
    @Singleton
    fun provideAuthAuthenticator(sessionRepository: SessionRepository): AuthAuthenticator =
        AuthAuthenticator(sessionRepository)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        authenticateApi: AuthenticateApiService
    ): AuthRepository =
        AuthRepositoryImpl(
            api = api,
            authenticateApi = authenticateApi
        )

    @Provides
    @Singleton
    fun provideOnBoardingRepository(
        @ApplicationContext context: Context
    ): OnBoardingRepository =
        OnBoardingRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideGetAccessTokenUseCase(
        repository: SessionRepository
    ) = GetAccessTokenUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveOnBoardingStateUseCase(
        repository: OnBoardingRepository
    ) = SaveOnBoardingStateUseCase(repository)

    @Provides
    @Singleton
    fun provideReadOnBoardingStateUseCase(
        repository: OnBoardingRepository
    ) = ReadOnBoardingStateUseCase(repository)

    @Provides
    @Singleton
    fun providesAuthUseCases(
        authRepository: AuthRepository
    ): AuthUseCases =
        AuthUseCases(
            getOtp = GetOtpUseCase(authRepository),
            verifyOtp = VerifyOtpUseCase(authRepository),
            googleSignIn = GoogleSignInUseCase(authRepository)
        )

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(
        authRepository: AuthRepository
    ): AuthenticateUseCase =
        AuthenticateUseCase(authRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        sessionRepository: SessionRepository
    ): LogoutUseCase = LogoutUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideSaveUserIdUseCase(
        sessionRepository: SessionRepository
    ): SaveUserIdUseCase = SaveUserIdUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideSaveAccessTokenUseCase(
        sessionRepository: SessionRepository
    ): SaveAccessTokenUseCase = SaveAccessTokenUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideSaveRefreshTokenUseCase(
        sessionRepository: SessionRepository
    ): SaveRefreshTokenUseCase = SaveRefreshTokenUseCase(sessionRepository)
}