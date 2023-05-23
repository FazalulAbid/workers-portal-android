package com.fifty.workersportal.di

import android.provider.SyncStateContract.Constants
import com.fifty.workersportal.featureauth.data.remote.CountryCodeApi
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object CountryModule {

    @Provides
    @Singleton
    fun provideResCountryService(client: OkHttpClient): CountryCodeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.ACCOUNT_NAME)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryCodeApi::class.java)
    }
}