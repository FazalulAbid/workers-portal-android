package com.fifty.workersportal.di

import android.app.Application
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}