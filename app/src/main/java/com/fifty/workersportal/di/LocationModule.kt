package com.fifty.workersportal.di

import android.content.Context
import android.location.Geocoder
import com.fifty.workersportal.featurelocation.data.remote.GeocodeApiService
import com.fifty.workersportal.featurelocation.data.remote.LocationApiService
import com.fifty.workersportal.featurelocation.data.repository.LocationRepositoryImpl
import com.fifty.workersportal.featurelocation.domain.repository.LocationRepository
import com.fifty.workersportal.featurelocation.domain.usecase.CheckIfDeviceLocationEnabledUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressFromLatLngUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressesOfUserUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetCurrentLocationUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressFromReverseGeocodingUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.ReverseGeocodeForDisplayAddressUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.SaveAddressUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.SelectLocalAddressUseCase
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
    fun provideGeocodeApiService(
        retrofit: Retrofit.Builder
    ): GeocodeApiService =
        retrofit
            .build()
            .create(GeocodeApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationApi: LocationApiService,
        geocodeApi: GeocodeApiService
    ): LocationRepository =
        LocationRepositoryImpl(locationApi, geocodeApi)

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
    fun provideGetLocalAddressFromReverseGeocodingUseCase(): GetLocalAddressFromReverseGeocodingUseCase =
        GetLocalAddressFromReverseGeocodingUseCase()

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

    @Provides
    @Singleton
    fun provideGetAddressesOfUserUseCase(repository: LocationRepository): GetAddressesOfUserUseCase =
        GetAddressesOfUserUseCase(repository)

    @Provides
    @Singleton
    fun provideSelectLocalAddressUseCase(repository: LocationRepository): SelectLocalAddressUseCase =
        SelectLocalAddressUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLocalAddressUseCase(repository: LocationRepository): GetLocalAddressUseCase =
        GetLocalAddressUseCase(repository)

    @Provides
    @Singleton
    fun provideReverseGeocodeForDisplayAddressUseCase(repository: LocationRepository): ReverseGeocodeForDisplayAddressUseCase =
        ReverseGeocodeForDisplayAddressUseCase(repository)

}