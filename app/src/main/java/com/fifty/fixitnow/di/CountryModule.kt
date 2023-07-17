package com.fifty.fixitnow.di

import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featureauth.data.remote.CountryCodeApiService
import com.fifty.fixitnow.featureauth.data.repository.CountryCodeRepositoryImpl
import com.fifty.fixitnow.featureauth.domain.repository.CountryCodeRepository
import com.fifty.fixitnow.featureauth.domain.usecase.GetCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountryModule {

    @Provides
    @Singleton
    fun provideRestCountryApi(retrofit: Retrofit.Builder): CountryCodeApiService =
        retrofit
            .baseUrl(Constants.REST_COUNTRIES_BASE_URL)
            .build()
            .create(CountryCodeApiService::class.java)


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