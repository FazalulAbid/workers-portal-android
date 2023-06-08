package com.fifty.workersportal.di

import com.fifty.workersportal.core.domain.usecase.GetUserProfileDetailsUseCase
import com.fifty.workersportal.featureauth.domain.repository.SessionRepository
import com.fifty.workersportal.featureuser.data.remote.ProfileApiService
import com.fifty.workersportal.featureuser.data.repository.ProfileRepositoryImpl
import com.fifty.workersportal.featureuser.domain.repository.ProfileRepository
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.fifty.workersportal.featureworker.domain.usecase.GetCategoriesUseCase
import com.fifty.workersportal.featureworker.domain.usecase.WorkerUseCases
import com.fifty.workersportal.featureworker.domain.usecase.SetSkillSelectedUseCase
import com.fifty.workersportal.featureworker.domain.usecase.UpdateUserAsWorkerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit.Builder): ProfileApiService =
        retrofit
            .build()
            .create(ProfileApiService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(api: ProfileApiService): ProfileRepository =
        ProfileRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesRegisterAsWorkerUseCases(
        profileRepository: ProfileRepository,
        sessionRepository: SessionRepository,
        workerRepository: WorkerRepository
    ): WorkerUseCases =
        WorkerUseCases(
            setSkillSelected = SetSkillSelectedUseCase(),
            updateUserAsWorker = UpdateUserAsWorkerUseCase(profileRepository, sessionRepository),
            getUserProfileDetails = GetUserProfileDetailsUseCase(profileRepository),
            getCategories = GetCategoriesUseCase(workerRepository)
        )
}