package com.fifty.workersportal.di

import android.content.Context
import android.location.Geocoder
import com.fifty.workersportal.featurelocation.data.remote.LocationApiService
import com.fifty.workersportal.featurelocation.data.repository.LocationRepositoryImpl
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository
import com.fifty.workersportal.featurelocation.domain.usecase.CheckIfDeviceLocationEnabledUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressFromLatLngUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetCurrentLocationUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressFromAddressUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.SaveAddressUseCase
import com.fifty.workersportal.featureworker.data.remote.WorkerApiService
import com.fifty.workersportal.featureworker.data.repository.WorkerRepositoryImpl
import com.fifty.workersportal.featureworker.domain.repository.WorkerRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationApiService(
        client: OkHttpClient,
        retrofit: Retrofit.Builder
    ): LocationApiService =
        retrofit
            .client(client)
            .build()
            .create(LocationApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationRepository(api: LocationApiService): LocationRepository =
        LocationRepositoryImpl(api)

    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder =
        Geocoder(context, Locale.getDefault())

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(fusedLocationProviderClient: FusedLocationProviderClient) =
        GetCurrentLocationUseCase(fusedLocationProviderClient)

    @Provides
    @Singleton
    fun provideGetAddressFromLatLngUseCase(geocoder: Geocoder): GetAddressFromLatLngUseCase =
        GetAddressFromLatLngUseCase(geocoder)

    @Provides
    @Singleton
    fun provideGetLocalAddressFromAddressUseCase(): GetLocalAddressFromAddressUseCase =
        GetLocalAddressFromAddressUseCase()

    @Provides
    @Singleton
    fun provideCheckIfDeviceLocationEnabledUseCase(
        settingsClient: SettingsClient
    ): CheckIfDeviceLocationEnabledUseCase =
        CheckIfDeviceLocationEnabledUseCase(settingsClient)

    @Provides
    @Singleton
    fun provideSaveAddressUseCase(repository: LocationRepository): SaveAddressUseCase =
        SaveAddressUseCase(repository)

}