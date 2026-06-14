package com.lumina.sunchaser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lumina.sunchaser.presentation.main.MainScreen
import com.lumina.sunchaser.presentation.main.MainViewModel
import com.lumina.sunchaser.domain.usecase.CalculateRatingUseCase
import com.lumina.sunchaser.domain.usecase.GetAiRecommendationUseCase
import com.lumina.sunchaser.data.remote.OpenMeteoService
import com.lumina.sunchaser.data.repository.WeatherRepositoryImpl
import com.lumina.sunchaser.data.remote.OnlySqService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ручная инициализация для прототипа (заменяет Hilt)
        val weatherApi = Retrofit.Builder()
            .baseUrl(OpenMeteoService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoService::class.java)
            
        val aiApi = Retrofit.Builder()
            .baseUrl(OnlySqService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OnlySqService::class.java)
            
        val repo = WeatherRepositoryImpl(weatherApi)
        val calcRating = CalculateRatingUseCase()
        val aiAdvice = GetAiRecommendationUseCase(aiApi)
        
        val viewModel = MainViewModel(repo, calcRating, aiAdvice)
        
        setContent {
            MainScreen(viewModel)
        }
    }
}
