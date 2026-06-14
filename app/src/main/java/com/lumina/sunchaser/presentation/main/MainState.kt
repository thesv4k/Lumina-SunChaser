package com.lumina.sunchaser.presentation.main

import com.lumina.sunchaser.domain.model.WeatherData
import com.lumina.sunchaser.domain.model.AiRecommendation
import com.lumina.sunchaser.domain.model.UserProfile

data class MainState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val profile: UserProfile = UserProfile(),
    val aiRecommendation: AiRecommendation? = null,
    val rating: Int = 0,
    val verdict: String = "",
    val error: String? = null,
    val isNightVision: Boolean = false
)
