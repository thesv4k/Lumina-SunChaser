package com.lumina.sunchaser.data.remote

import com.google.gson.annotations.SerializedName

data class OpenMeteoResponse(
    @SerializedName("hourly") val hourly: HourlyData
)

data class HourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<Double>,
    @SerializedName("relative_humidity_2m") val humidity: List<Double>,
    @SerializedName("cloud_cover") val cloudCover: List<Int>,
    @SerializedName("cloud_cover_low") val cloudCoverLow: List<Int>,
    @SerializedName("cloud_cover_mid") val cloudCoverMid: List<Int>,
    @SerializedName("cloud_cover_high") val cloudCoverHigh: List<Int>,
    @SerializedName("visibility") val visibility: List<Double>,
    @SerializedName("wind_speed_10m") val windSpeed: List<Double>,
    @SerializedName("surface_pressure") val pressure: List<Double>,
    @SerializedName("fog") val fog: List<Int>
)
