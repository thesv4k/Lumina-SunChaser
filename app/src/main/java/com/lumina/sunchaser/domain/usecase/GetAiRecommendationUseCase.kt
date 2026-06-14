package com.lumina.sunchaser.domain.usecase

import com.lumina.sunchaser.data.remote.Message
import com.lumina.sunchaser.data.remote.OnlySqRequest
import com.lumina.sunchaser.data.remote.OnlySqService
import com.lumina.sunchaser.domain.model.AiRecommendation
import com.lumina.sunchaser.domain.model.UserProfile
import com.lumina.sunchaser.domain.model.WeatherData

class GetAiRecommendationUseCase (
    private val api: OnlySqService
) {
    suspend fun execute(weather: WeatherData, profile: UserProfile): Result<AiRecommendation?> {
        if (!profile.aiEnabled || profile.onlySqApiKey.isBlank()) {
            return Result.success(null)
        }

        return Result.runCatching {
            val prompt = """
                Ты эксперт-фотограф. Погода: облачность Ср:${weather.cloudCoverMid}%, Выс:${weather.cloudCoverHigh}%, Низ:${weather.cloudCoverLow}%. 
                Видимость ${weather.visibility}км, ветер ${weather.windSpeed}м/с, туман ${weather.fog}%.
                Профиль фотографа: опыт ${profile.experienceLevel}, стиль ${profile.preferredStyle}, штатив ${if(profile.hasTripod) "есть" else "нет"}, оборудование ${profile.equipment.joinToString()}.
                Дай совет в формате: 
                Вердикт: (ехать или нет), 
                Техника: (какой объектив и нужен ли штатив), 
                Композиция: (на что сделать акцент), 
                Настройки: (выдержка/диафрагма).
            """.trimIndent()

            val response = api.getRecommendation(
                "Bearer ${profile.onlySqApiKey}",
                OnlySqRequest(messages = listOf(Message("user", prompt)))
            )

            val text = response.choices.first().message.content
            parseRecommendation(text)
        }
    }

    private fun parseRecommendation(text: String): AiRecommendation {
        // Простой парсинг для примера, в проде лучше использовать Regex
        val lines = text.split("\n")
        return AiRecommendation(
            verdict = lines.find { it.startsWith("Вердикт") }?.substringAfter(":") ?: "Неизвестно",
            gearAdvice = lines.find { it.startsWith("Техника") }?.substringAfter(":") ?: "По ситуации",
            compositionTip = lines.find { it.startsWith("Композиция") }?.substringAfter(":") ?: "Стандартная",
            settingsAdvice = lines.find { it.startsWith("Настройки") }?.substringAfter(":") ?: "Авто"
        )
    }
}
