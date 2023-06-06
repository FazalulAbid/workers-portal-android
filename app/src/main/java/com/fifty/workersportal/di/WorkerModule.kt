package com.fifty.workersportal.di

import com.fifty.workersportal.featureworker.domain.usecase.RegisterAsWorkerUseCases
import com.fifty.workersportal.featureworker.domain.usecase.SetSkillSelectedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun providesRegisterAsWorkerUseCases(): RegisterAsWorkerUseCases =
        RegisterAsWorkerUseCases(
            setSkillSelected = SetSkillSelectedUseCase()
        )
}