package com.fifty.workersportal.di

import android.content.Context
import android.content.SharedPreferences
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.utils.TokenManager
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.repository.AuthRepositoryImpl
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.usecase.AuthUseCases
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
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(client: OkHttpClient, retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Provides
    @Singleton
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApiService, tokenManager: TokenManager): AuthRepository =
        AuthRepositoryImpl(api, tokenManager)

    @Provides
    @Singleton
    fun providesAuthUseCases(repository: AuthRepository): AuthUseCases =
        AuthUseCases(
            getOtp = GetOtpUseCase(repository),
            verifyOtp = VerifyOtpUseCase(repository)
        )
}