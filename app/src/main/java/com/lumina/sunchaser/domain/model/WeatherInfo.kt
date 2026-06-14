package com.lumina.sunchaser.domain.model

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperature: Double,
    val humidity: Double,
    val cloudCoverTotal: Int, // %
    val cloudCoverLow: Int,   // %
    val cloudCoverMid: Int,   // %
    val cloudCoverHigh: Int,  // %
    val visibility: Double,   // km
    val windSpeed: Double,    // m/s
    val pressure: Double,     // hPa
    val fog: Int              // %
)

data class SunEvents(
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val goldenHourMorningStart: LocalDateTime,
    val goldenHourMorningEnd: LocalDateTime,
    val goldenHourEveningStart: LocalDateTime,
    val goldenHourEveningEnd: LocalDateTime,
    val blueHourMorningStart: LocalDateTime,
    val blueHourMorningEnd: LocalDateTime,
    val blueHourEveningStart: LocalDateTime,
    val blueHourEveningEnd: LocalDateTime
)
