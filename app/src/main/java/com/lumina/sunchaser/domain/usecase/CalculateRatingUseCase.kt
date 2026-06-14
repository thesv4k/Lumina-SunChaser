package com.lumina.sunchaser.domain.usecase

import com.lumina.sunchaser.domain.model.WeatherData
import com.lumina.sunchaser.domain.model.UserProfile

class CalculateRatingUseCase () {

    /**
     * Возвращает рейтинг от 0 до 100 на основе погоды и профиля пользователя.
     */
    operator fun invoke(weather: WeatherData, profile: UserProfile): Int {
        var score = 0.0

        // 1. Облачность (база 40 баллов)
        val idealClouds = (weather.cloudCoverMid * 0.6 + weather.cloudCoverHigh * 1.0)
        score += when {
            idealClouds in 30.0..70.0 -> 40.0
            idealClouds > 70.0 -> 40.0 - (idealClouds - 70.0)
            else -> idealClouds * 0.5
        }

        // Драматичные облака для любителей драмы
        if (profile.dramaLover && weather.cloudCoverMid > 50) {
            score += 10.0
        }

        // Штраф за низкую плотную облачность
        if (weather.cloudCoverLow > 80) {
            score -= (weather.cloudCoverLow - 80) * 0.7
        }

        // 2. Прозрачность (база 20 баллов)
        score += (weather.visibility.coerceIn(0.0, 20.0) / 20.0) * 20.0

        // 3. Туман (база 20 баллов + персональный бонус)
        val fogScore = (weather.fog.coerceIn(0, 100) / 100.0) * 20.0
        score += if (profile.fogHunter) fogScore * 1.5 else fogScore

        // 4. Оборудование и риски
        // Если сильный ветер и нет штатива - штрафуем рейтинг
        if (weather.windSpeed > 8.0 && !profile.hasTripod) {
            score -= 15.0
        }

        return score.toInt().coerceIn(0, 100)
    }

    fun getVerdict(rating: Int): String {
        return when {
            rating >= 85 -> "Эпично. Обязательно к посещению!"
            rating >= 70 -> "Очень хорошо. Высокие шансы на свет."
            rating >= 50 -> "Средне. Может повезти."
            rating >= 30 -> "Слабо. Только для тренировки."
            else -> "Спите спокойно. Условий нет."
        }
    }
}
