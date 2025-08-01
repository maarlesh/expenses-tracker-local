package com.local_expenses.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset

val AppGradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF26003D),
        Color(0xFF7303c0),
        Color(0xFFec38bc)
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 0f)
)

val AppGradientBrush2 = Brush.linearGradient(
    colors = listOf(
        Color(0xFF26003D),
        Color(0xFF110020),
        Color(0xFF000000),
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 0f)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTextColor,
    secondary = SecondaryTextColor,
    tertiary = TernaryTextColor,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTextColor,
    secondary = SecondaryTextColor,
    tertiary = TernaryTextColor,
    primaryContainer = PrimaryButtonColor,
    secondaryContainer = SecondaryButtonColor

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun LocalexpensesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}