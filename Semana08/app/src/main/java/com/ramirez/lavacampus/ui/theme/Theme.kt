package com.ramirez.lavacampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ramirez.lavacampus.model.Rol

private val EstudianteLightScheme = lightColorScheme(
    primary = TurquesaPrimary,
    primaryContainer = TurquesaContainer,
    secondaryContainer = Color(0xFFCFFAFE),
    background = TurquesaBackground,
    surface = Color.White,
    surfaceVariant = Color.White,
    onSurfaceVariant = Color(0xFF0F172A),
    surfaceTint = Color.Transparent
)

private val AdminLightScheme = lightColorScheme(
    primary = VioletaPrimary,
    primaryContainer = VioletaContainer,
    secondaryContainer = Color(0xFFEDE9FE),
    background = Color(0xFFFAF8FF),
    surface = Color.White,
    surfaceVariant = Color.White,
    onSurfaceVariant = Color(0xFF0F172A),
    surfaceTint = Color.Transparent
)

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    primaryContainer = Color(0xFF0369A1),
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color.White,
    surfaceTint = Color.Transparent
)

@Composable
fun LavaTheme(
    rol: String = Rol.CLIENTE.name,
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        modoOscuro -> DarkScheme
        rol == Rol.ADMIN.name -> AdminLightScheme
        else -> EstudianteLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
