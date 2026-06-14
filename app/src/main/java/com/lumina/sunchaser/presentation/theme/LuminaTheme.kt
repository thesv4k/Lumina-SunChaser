package com.lumina.sunchaser.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val NightVisionColorScheme = darkColorScheme(
    primary = Color(0xFFFF0000), // Ярко-красный
    onPrimary = Color.Black,
    surface = Color.Black,
    onSurface = Color(0xFF880000), // Тускло-красный
    background = Color.Black,
    onBackground = Color(0xFFBB0000)
)

val DayColorScheme = darkColorScheme(
    primary = Color(0xFFFFB74D), // Оранжевый закат
    secondary = Color(0xFF64B5F6) // Голубой рассвет
)
