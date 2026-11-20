package com.ramirez.centrotareas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CentroTareasColors = darkColorScheme(
    primary = Naranja,
    onPrimary = Fondo,
    primaryContainer = NaranjaSuave,
    onPrimaryContainer = Naranja,
    secondary = Naranja,
    onSecondary = Fondo,
    secondaryContainer = Superficie,
    onSecondaryContainer = Texto,
    tertiary = Verde,
    onTertiary = Fondo,
    background = Fondo,
    onBackground = Texto,
    surface = Tarjeta,
    onSurface = Texto,
    surfaceVariant = Superficie,
    onSurfaceVariant = TextoSecundario,
    surfaceContainer = Superficie,
    surfaceContainerLow = Tarjeta,
    surfaceContainerHigh = Superficie,
    outline = Color(0xFF363D51),
    outlineVariant = Color(0xFF252A38),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF492421),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun CentrotareasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CentroTareasColors,
        typography = Typography,
        content = content
    )
}
