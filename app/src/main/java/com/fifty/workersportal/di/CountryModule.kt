package com.fifty.workersportal.di

import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.data.remote.CountryCodeApiService
import com.fifty.workersportal.featureauth.data.repository.CountryCodeRepositoryImpl
import com.fifty.workersportal.featureauth.domain.repository.CountryCodeRepository
import com.fifty.workersportal.featureauth.domain.usecase.GetCountriesUseCase
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
object CountryModule {

    @Provides
    @Singleton
    fun provideRestCountryApi(client: OkHttpClient): CountryCodeApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.REST_COUNTRIES_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryCodeApiService::class.java)
    }

    @Provides
    @Singleton
    fun CountryCodeRepository(api: CountryCodeApiService): CountryCodeRepository {
        return CountryCodeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(repository: CountryCodeRepository): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
    }
}