package com.lumina.sunchaser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumina.sunchaser.domain.repository.WeatherRepository
import com.lumina.sunchaser.domain.usecase.CalculateRatingUseCase
import com.lumina.sunchaser.domain.usecase.GetAiRecommendationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val calculateRatingUseCase: CalculateRatingUseCase,
    private val getAiRecommendationUseCase: GetAiRecommendationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        loadForecast()
    }

    fun toggleNightVision() {
        _state.update { it.copy(isNightVision = !it.isNightVision) }
    }

    fun loadForecast() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = repository.getForecast(55.7512, 37.6184)
            
            result.onSuccess { forecasts ->
                val current = forecasts.firstOrNull()
                if (current != null) {
                    val rating = calculateRatingUseCase(current, _state.value.profile)
                    val verdict = calculateRatingUseCase.getVerdict(rating)
                    
                    // Загружаем ИИ рекомендации
                    val aiResult = getAiRecommendationUseCase.execute(current, _state.value.profile)
                    
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            weatherData = current,
                            rating = rating,
                            verdict = verdict,
                            aiRecommendation = aiResult.getOrNull()
                        )
                    }
                }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
