package com.lumina.sunchaser.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumina.sunchaser.presentation.theme.NightVisionColorScheme
import com.lumina.sunchaser.presentation.theme.DayColorScheme

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    
    val colorScheme = if (state.isNightVision) NightVisionColorScheme else MaterialTheme.colorScheme

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Lumina SunChaser", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = { viewModel.toggleNightVision() }) {
                            Icon(
                                if (state.isNightVision) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Night Vision"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Рейтинг фотогеничности", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${state.rating}",
                            fontSize = 84.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (state.isNightVision) Color.Red else getRatingColor(state.rating)
                        )
                        Text(state.verdict, style = MaterialTheme.typography.headlineSmall, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        state.weatherData?.let { WeatherDetailGrid(it) }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        if (state.aiRecommendation != null) {
                            AiRecommendationCard(state.aiRecommendation!!)
                        } else if (state.profile.aiEnabled && state.profile.onlySqApiKey.isBlank()) {
                            Text("Введите API ключ OnlySQ в настройках для ИИ советов", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiRecommendationCard(ai: com.lumina.sunchaser.domain.model.AiRecommendation) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("AI Анализ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Совет: ${ai.verdict}", fontWeight = FontWeight.Bold)
            Text("Техника: ${ai.gearAdvice}")
            Text("Композиция: ${ai.compositionTip}")
            Text("Настройки: ${ai.settingsAdvice}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun WeatherDetailGrid(weather: com.lumina.sunchaser.domain.model.WeatherData) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DetailItem("Средние обл.", "${weather.cloudCoverMid}%")
                DetailItem("Высокие обл.", "${weather.cloudCoverHigh}%")
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DetailItem("Видимость", "${weather.visibility} км")
                DetailItem("Туман", "${weather.fog}%")
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

fun getRatingColor(rating: Int): Color {
    return when {
        rating >= 80 -> Color(0xFF4CAF50)
        rating >= 60 -> Color(0xFFFFC107)
        rating >= 40 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}
