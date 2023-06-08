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
        authRepository: AuthRepository,
        sessionRepository: SessionRepository
    ): AuthUseCases =
        AuthUseCases(
            getOtp = GetOtpUseCase(authRepository),
            verifyOtp = VerifyOtpUseCase(authRepository, sessionRepository)
        )

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(
        authRepository: AuthRepository,
        sessionRepository: SessionRepository
    ): AuthenticateUseCase =
        AuthenticateUseCase(authRepository, sessionRepository)
}