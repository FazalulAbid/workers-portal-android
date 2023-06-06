package com.fifty.workersportal.di

import com.fifty.workersportal.featureworker.data.repository.WorkerRepositoryImpl
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.fifty.workersportal.featureworker.domain.usecase.RegisterAsWorkerUseCases
import com.fifty.workersportal.featureworker.domain.usecase.SetSkillSelectedUseCase
import com.fifty.workersportal.featureworker.domain.usecase.UpdateWorkerUseCase
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
    fun providesWorkerRepository(): WorkerRepository =
        WorkerRepositoryImpl()

    @Provides
    @Singleton
    fun providesRegisterAsWorkerUseCases(repository: WorkerRepository): RegisterAsWorkerUseCases =
        RegisterAsWorkerUseCases(
            setSkillSelected = SetSkillSelectedUseCase(),
            updateWorker = UpdateWorkerUseCase(repository)
        )
}