package com.example.weatherapp.di

import com.example.weatherapp.common.adapter.UnixTimestampDateJsonAdapter
import com.example.weatherapp.feature.fetchcityname.data.AppConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideAppConfig(): AppConfig{
        return AppConfig(
            weatherBaseUrl = "https://api.openweathermap.org/data/2.5/",
            reverseGeocodingBaseUrl = "https://api.openweathermap.org/geo/1.0/",
            apiKey = "5fe4e1715cf27ea3002f0c66ebda3d51"
        )
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UnixTimestampDateJsonAdapter())
        .build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            interceptor
        ).build()

}