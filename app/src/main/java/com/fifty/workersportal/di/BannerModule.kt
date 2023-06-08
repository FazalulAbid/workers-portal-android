package com.fifty.workersportal.di

import com.fifty.workersportal.featureuser.data.remote.BannerApiService
import com.fifty.workersportal.featureuser.data.repository.BannerRepositoryImpl
import com.fifty.workersportal.featureuser.domain.model.Banner
import com.fifty.workersportal.featureuser.domain.repository.BannerRepository
import com.fifty.workersportal.featureuser.domain.usecase.GetDashboardBannersUseCase
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BannerModule {

    @Provides
    @Singleton
    fun provideBannerApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): BannerApiService =
        retrofit
            .client(client)
            .build()
            .create(BannerApiService::class.java)

    @Provides
    @Singleton
    fun provideBannerRepository(api: BannerApiService): BannerRepository =
        BannerRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetDashboardBannersUseCase(repository: BannerRepository): GetDashboardBannersUseCase =
        GetDashboardBannersUseCase(repository)
}