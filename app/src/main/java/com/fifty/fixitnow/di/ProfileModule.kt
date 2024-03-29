package com.fifty.fixitnow.di

import com.fifty.fixitnow.core.domain.usecase.GetProfileDetailsUseCase
import com.fifty.fixitnow.featureuser.data.remote.ProfileApiService
import com.fifty.fixitnow.featureuser.data.repository.ProfileRepositoryImpl
import com.fifty.fixitnow.featureuser.domain.repository.ProfileRepository
import com.fifty.fixitnow.featureuser.domain.usecase.GetUserProfileUseCase
import com.fifty.fixitnow.featureuser.domain.usecase.UpdateUserProfileUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.SetSkillSelectedUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.UpdateUserAsWorkerUseCase
import com.fifty.fixitnow.featureworker.domain.usecase.ValidateUserAsWorkerUseCase
import com.google.gson.Gson
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
    fun provideUserRepository(api: ProfileApiService, gson: Gson): ProfileRepository =
        ProfileRepositoryImpl(api, gson)

    @Provides
    @Singleton
    fun provideSetSkillSelectedUseCase(): SetSkillSelectedUseCase =
        SetSkillSelectedUseCase()

    @Provides
    @Singleton
    fun provideValidateUserAsWorkerUseCase(): ValidateUserAsWorkerUseCase =
        ValidateUserAsWorkerUseCase()

    @Provides
    @Singleton
    fun provideUpdateUserAsWorkerUseCase(
        profileRepository: ProfileRepository
    ): UpdateUserAsWorkerUseCase =
        UpdateUserAsWorkerUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideUpdateUserProfileUseCase(
        profileRepository: ProfileRepository
    ): UpdateUserProfileUseCase =
        UpdateUserProfileUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideGetProfileDetailsUseCase(
        profileRepository: ProfileRepository
    ): GetProfileDetailsUseCase =
        GetProfileDetailsUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        profileRepository: ProfileRepository
    ): GetUserProfileUseCase =
        GetUserProfileUseCase(profileRepository)

}