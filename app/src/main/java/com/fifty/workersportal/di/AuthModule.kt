package com.fifty.workersportal.di

import android.content.Context
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.remote.AuthenticateApiService
import com.fifty.workersportal.featureauth.data.repository.AuthRepositoryImpl
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.usecase.AuthUseCases
import com.fifty.workersportal.featureauth.domain.usecase.AuthenticateUseCase
import com.fifty.workersportal.featureauth.domain.usecase.GetOtpUseCase
import com.fifty.workersportal.featureauth.domain.usecase.VerifyOtpUseCase
import com.fifty.workersportal.featureauth.utils.AuthAuthenticator
import com.fifty.workersportal.featureauth.utils.AuthInterceptor
import com.fifty.workersportal.featureauth.utils.SessionManager
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
    fun provideTokenManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor =
        AuthInterceptor(sessionManager)

    @Provides
    @Singleton
    fun provideAuthAuthenticator(sessionManager: SessionManager): AuthAuthenticator =
        AuthAuthenticator(sessionManager)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        authenticateApi: AuthenticateApiService,
        sessionManager: SessionManager
    ): AuthRepository =
        AuthRepositoryImpl(
            api = api,
            authenticateApi = authenticateApi,
            sessionManager = sessionManager
        )

    @Provides
    @Singleton
    fun providesAuthUseCases(repository: AuthRepository): AuthUseCases =
        AuthUseCases(
            getOtp = GetOtpUseCase(repository),
            verifyOtp = VerifyOtpUseCase(repository)
        )

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(repository: AuthRepository): AuthenticateUseCase =
        AuthenticateUseCase(repository)
}