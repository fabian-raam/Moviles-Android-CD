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
    secondaryContainer = Color(0xFFE0F2FE),
    background = TurquesaBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9)
)

private val AdminLightScheme = lightColorScheme(
    primary = VioletaPrimary,
    primaryContainer = VioletaContainer,
    secondaryContainer = Color(0xFFF3E8FF),
    background = VioletaBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFF5F3FF)
)

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    primaryContainer = Color(0xFF0369A1),
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF334155)
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