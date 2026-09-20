package com.parkingtoiletfinder.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AppColors.Accent,
    onPrimary = AppColors.Background,
    secondary = AppColors.Accent2,
    background = AppColors.Background,
    onBackground = AppColors.TextPrimary,
    surface = AppColors.Surface,
    onSurface = AppColors.TextPrimary,
    surfaceVariant = AppColors.Neutral200,
    outline = AppColors.Divider,
    error = AppColors.StatusBad,
)

private val DarkColors = darkColorScheme(
    primary = AppColors.Accent300,
    onPrimary = AppColors.Neutral900,
    secondary = AppColors.Accent2,
    background = AppColors.Neutral900,
    onBackground = AppColors.Neutral100,
    surface = AppColors.Neutral800,
    onSurface = AppColors.Neutral100,
    surfaceVariant = AppColors.Neutral700,
    outline = AppColors.Neutral600,
    error = AppColors.StatusBad,
)

@Composable
fun ParkingToiletFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}
