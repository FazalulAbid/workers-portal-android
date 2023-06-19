package com.fifty.workersportal.di

import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.repository.ReviewAndRatingRepositoryImpl
import com.fifty.workersportal.featureworker.data.repository.WorkerRepositoryImpl
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.fifty.workersportal.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSuggestedCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.PostReviewAndRatingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideWorkerApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): WorkerApiService =
        retrofit
            .client(client)
            .build()
            .create(WorkerApiService::class.java)

    @Provides
    @Singleton
    fun provideWorkerRepository(api: WorkerApiService): WorkerRepository =
        WorkerRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideReviewAndRatingRepository(api: WorkerApiService): ReviewAndRatingRepository =
        ReviewAndRatingRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        workerRepository: WorkerRepository
    ): GetCategoriesUseCase =
        GetCategoriesUseCase(workerRepository)

    @Provides
    @Singleton
    fun provideGetSuggestedCategoriesUseCase(
        repository: WorkerRepository
    ): GetSuggestedCategoriesUseCase =
        GetSuggestedCategoriesUseCase(repository)

    @Provides
    @Singleton
    fun providePostReviewAndRatingUseCase(
        repository: ReviewAndRatingRepository
    ): PostReviewAndRatingUseCase =
        PostReviewAndRatingUseCase(repository)
}