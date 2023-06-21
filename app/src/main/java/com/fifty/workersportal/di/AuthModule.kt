package com.fifty.workersportal.di

import android.content.Context
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.remote.AuthenticateApiService
import com.fifty.workersportal.featureauth.data.repository.AuthRepositoryImpl
import com.fifty.workersportal.featureauth.data.repository.SessionRepositoryImpl
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureauth.domain.usecase.AuthUseCases
import com.fifty.workersportal.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.workersportal.featureauth.domain.usecase.GetOtpUseCase
import com.fifty.workersportal.featureauth.domain.usecase.GetUserSessionUseCase
import com.fifty.workersportal.featureauth.domain.usecase.LogoutUseCase
import com.fifty.workersportal.featureauth.domain.usecase.SaveAccessTokenUseCase
import com.fifty.workersportal.featureauth.domain.usecase.SaveRefreshTokenUseCase
import com.fifty.workersportal.featureauth.domain.usecase.SaveUserSessionUseCase
import com.fifty.workersportal.featureauth.domain.usecase.VerifyOtpUseCase
import com.fifty.workersportal.featureauth.utils.AuthAuthenticator
import com.fifty.workersportal.featureauth.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.POST
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
    fun providesAuthUseCases(
        authRepository: AuthRepository
    ): AuthUseCases =
        AuthUseCases(
            getOtp = GetOtpUseCase(authRepository),
            verifyOtp = VerifyOtpUseCase(authRepository)
        )

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(
        authRepository: AuthRepository,
        sessionRepository: SessionRepository
    ): AuthenticateUseCase =
        AuthenticateUseCase(authRepository, sessionRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        sessionRepository: SessionRepository
    ): LogoutUseCase = LogoutUseCase(sessionRepository)

    @Provides
    @Singleton
    fun provideSaveUserSessionUseCase(
        sessionRepository: SessionRepository
    ): SaveUserSessionUseCase = SaveUserSessionUseCase(sessionRepository)

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

    @Provides
    @Singleton
    fun provideGetUserSessionUseCase(
        sessionRepository: SessionRepository
    ): GetUserSessionUseCase = GetUserSessionUseCase(sessionRepository)
}