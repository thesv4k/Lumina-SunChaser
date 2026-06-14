package com.lumina.sunchaser.core.di

import com.lumina.sunchaser.data.remote.OpenMeteoService
import com.lumina.sunchaser.data.repository.WeatherRepositoryImpl
import com.lumina.sunchaser.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOpenMeteoService(): OpenMeteoService {
        return Retrofit.Builder()
            .baseUrl(OpenMeteoService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenMeteoService): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}
