package com.fifty.fixitnow.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.fifty.fixitnow.WorkersPortalApplication
import com.fifty.fixitnow.core.domain.usecase.GetOwnUserIdUseCase
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.DefaultFavouriteToggle
import com.fifty.fixitnow.core.util.FavouriteToggle
import com.fifty.fixitnow.featureauth.domain.repository.SessionRepository
import com.fifty.fixitnow.featureauth.domain.usecase.GetAccessTokenUseCase
import com.fifty.fixitnow.featureauth.utils.AuthAuthenticator
import com.fifty.fixitnow.featureauth.utils.AuthInterceptor
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.WORKERS_PORTAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader =
        ImageLoader.Builder(app)
            .crossfade(true)
            .crossfade(200)
            .componentRegistry {
                add(SvgDecoder(app))
            }
            .build()

    @Provides
    @Singleton
    fun provideSettingsClient(
        @ApplicationContext context: Context
    ): SettingsClient =
        LocationServices.getSettingsClient(context)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideFavouriteToggle(): FavouriteToggle =
        DefaultFavouriteToggle()

    @Provides
    @Singleton
    fun provideGetOwnUserId(repository: SessionRepository): GetOwnUserIdUseCase =
        GetOwnUserIdUseCase(repository)
}