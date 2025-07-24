package com.challange.lumiparser.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Gray94,
    secondary = White,
    tertiary = Gray87,
    onPrimary = Gray24,
    onSecondary= Gray24,
    onTertiary = Blue
)

private val DarkColorScheme = darkColorScheme(
    primary = Gray9,
    secondary = Gray17,
    tertiary = Gray30,
    onPrimary = Gray91,
    onSecondary= Gray91,
    onTertiary = Blue
)

@Composable
fun LumiParserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}