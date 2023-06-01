package com.fifty.workersportal.di

import android.content.SharedPreferences
import com.fifty.workersportal.featureauth.utils.TokenManager
import com.fifty.workersportal.featureauth.data.remote.AuthApiService
import com.fifty.workersportal.featureauth.data.repository.AuthRepositoryImpl
import com.fifty.workersportal.featureauth.domain.repository.AuthRepository
import com.fifty.workersportal.featureauth.domain.usecase.AuthUseCases
import com.fifty.workersportal.featureauth.domain.usecase.GetOtpUseCase
import com.fifty.workersportal.featureauth.domain.usecase.VerifyOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAuthApi(client: OkHttpClient): AuthApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AuthApiService.BASE_URL)
            .client(client)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApiService, tokenManager: TokenManager): AuthRepository {
        return AuthRepositoryImpl(api, tokenManager)
    }

    @Provides
    @Singleton
    fun providesAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            getOtp = GetOtpUseCase(repository),
            verifyOtp = VerifyOtpUseCase(repository)
        )
    }
}