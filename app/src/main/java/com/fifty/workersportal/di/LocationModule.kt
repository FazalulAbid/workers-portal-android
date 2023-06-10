package com.fifty.workersportal.di

import android.content.Context
import android.location.Geocoder
import com.fifty.workersportal.featurelocation.domain.usecase.GetAddressFromLatLngUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetCurrentLocationUseCase
import com.fifty.workersportal.featurelocation.domain.usecase.GetLocalAddressFromAddressUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

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
    fun provideGetLocalAddressFromAddress(): GetLocalAddressFromAddressUseCase =
        GetLocalAddressFromAddressUseCase()
}