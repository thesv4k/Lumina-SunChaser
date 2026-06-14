package com.lumina.sunchaser.domain.model

data class UserProfile(
    val id: Int = 1,
    val experienceLevel: String = "Любитель", // Новичок, Любитель, Профессионал
    val preferredStyle: String = "Пейзаж",
    val hasTripod: Boolean = true,
    val mobility: String = "Автомобиль", // Пешеход, Автомобиль
    val fogHunter: Boolean = false,
    val dramaLover: Boolean = true,
    val workRadius: Int = 50,
    val equipment: List<String> = listOf("24-70mm"),
    val aiEnabled: Boolean = true,
    val onlySqApiKey: String = ""
)

data class AiRecommendation(
    val verdict: String,
    val gearAdvice: String,
    val compositionTip: String,
    val settingsAdvice: String
)
