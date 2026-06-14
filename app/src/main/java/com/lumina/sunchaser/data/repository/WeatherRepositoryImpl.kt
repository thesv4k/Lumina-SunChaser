package com.lumina.sunchaser.data.repository

import com.lumina.sunchaser.data.remote.OpenMeteoService
import com.lumina.sunchaser.domain.model.WeatherData
import com.lumina.sunchaser.domain.repository.WeatherRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherRepositoryImpl (
    private val api: OpenMeteoService
) : WeatherRepository {

    override suspend fun getForecast(lat: Double, lon: Double): Result<List<WeatherData>> {
        return Result.runCatching {
            val response = api.getForecast(lat, lon)
            val hourly = response.hourly
            
            hourly.time.mapIndexed { index, timeString ->
                WeatherData(
                    time = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    temperature = hourly.temperature[index],
                    humidity = hourly.humidity[index],
                    cloudCoverTotal = hourly.cloudCover[index],
                    cloudCoverLow = hourly.cloudCoverLow[index],
                    cloudCoverMid = hourly.cloudCoverMid[index],
                    cloudCoverHigh = hourly.cloudCoverHigh[index],
                    visibility = hourly.visibility[index],
                    windSpeed = hourly.windSpeed[index],
                    pressure = hourly.pressure[index],
                    fog = hourly.fog[index]
                )
            }
        }
    }
}
