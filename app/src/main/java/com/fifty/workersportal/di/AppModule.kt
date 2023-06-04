package com.fifty.workersportal.di

import android.content.Context
import com.fifty.workersportal.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureauth.utils.AuthAuthenticator
import com.fifty.workersportal.featureauth.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.WORKERS_PORTAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideGetOwnUserId(repository: SessionRepository): GetOwnUserIdUseCase =
        GetOwnUserIdUseCase(repository)
}