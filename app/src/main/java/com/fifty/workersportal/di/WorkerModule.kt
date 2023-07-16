package com.fifty.workersportal.di

import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.repository.ReviewAndRatingRepositoryImpl
import com.fifty.workersportal.featureworker.data.repository.SampleWorkRepositoryImpl
import com.fifty.workersportal.featureworker.data.repository.WorkerRepositoryImpl
import com.fifty.workersportal.featureworker.domain.repository.ReviewAndRatingRepository
import com.fifty.workersportal.featureworker.domain.repository.SampleWorkRepository
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.fifty.workersportal.featureworker.domain.usecase.DeleteSampleWorkUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetFavouritesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetReviewAndRatingUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSampleWorkUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSampleWorksUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSearchedSortedAndFilteredWorkersUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetSuggestedCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetWorkerDetailsUseCase
import com.fifty.workersportal.featureworker.domain.usecase.GetWorkerRatingsCountUseCase
import com.fifty.workersportal.featureworker.domain.usecase.PostReviewAndRatingUseCase
import com.fifty.workersportal.featureworker.domain.usecase.PostSampleWorkUseCase
import com.fifty.workersportal.featureworker.domain.usecase.SearchCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.ToggleFavouriteWorkerUseCase
import com.fifty.workersportal.featureworker.domain.usecase.ToggleOpenToWorkUseCase
import com.google.gson.Gson
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
    fun provideSampleWorkRepository(api: WorkerApiService, gson: Gson): SampleWorkRepository =
        SampleWorkRepositoryImpl(api, gson)

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

    @Provides
    @Singleton
    fun provideToggleOpenToWorkUseCase(
        repository: WorkerRepository
    ): ToggleOpenToWorkUseCase =
        ToggleOpenToWorkUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleFavouriteWorkerUseCase(
        repository: WorkerRepository
    ): ToggleFavouriteWorkerUseCase =
        ToggleFavouriteWorkerUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchCategoriesUseCase(
        repository: WorkerRepository
    ): SearchCategoriesUseCase = SearchCategoriesUseCase(repository)

    @Provides
    @Singleton
    fun providePostSampleWorkUseCase(
        repository: SampleWorkRepository
    ): PostSampleWorkUseCase =
        PostSampleWorkUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSampleWorksUseCase(
        repository: SampleWorkRepository
    ): GetSampleWorksUseCase = GetSampleWorksUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSampleWorkUseCase(
        repository: SampleWorkRepository
    ): GetSampleWorkUseCase = GetSampleWorkUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteSampleWork(
        repository: SampleWorkRepository
    ): DeleteSampleWorkUseCase = DeleteSampleWorkUseCase(repository)

    @Provides
    @Singleton
    fun provideGetReviewAndRatingUseCase(
        repository: ReviewAndRatingRepository
    ): GetReviewAndRatingUseCase = GetReviewAndRatingUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSearchedSortedAndFilteredWorkersUseCase(
        repository: WorkerRepository
    ): GetSearchedSortedAndFilteredWorkersUseCase =
        GetSearchedSortedAndFilteredWorkersUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWorkerDetails(
        repository: WorkerRepository
    ): GetWorkerDetailsUseCase = GetWorkerDetailsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavouritesUseCase(
        repository: WorkerRepository
    ): GetFavouritesUseCase = GetFavouritesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWorkerRatingsCountUseCase(
        repository: ReviewAndRatingRepository
    ): GetWorkerRatingsCountUseCase = GetWorkerRatingsCountUseCase(repository)
}