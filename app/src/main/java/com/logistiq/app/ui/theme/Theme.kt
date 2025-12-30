package com.logistiq.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = LogistiQGreen,
    secondary = LogistiQLightGreen,
    tertiary = LogistiQBackground
)

private val LightColorScheme = lightColorScheme(
    primary = LogistiQGreen,
    onPrimary = LogistiQOnPrimary,

    secondary = LogistiQLightGreen,
    onSecondary = LogistiQOnSecondary,

    background = LogistiQBackground,
    onBackground = LogistiQOnBackground,

    surface = LogistiQSurface,
    onSurface = LogistiQOnBackground,
)

@Composable
fun LogistiQTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme){
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}