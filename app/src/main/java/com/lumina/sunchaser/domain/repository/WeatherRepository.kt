package com.lumina.sunchaser.domain.repository

import com.lumina.sunchaser.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getForecast(lat: Double, lon: Double): Result<List<WeatherData>>
}
