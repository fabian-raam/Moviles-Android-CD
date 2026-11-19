package com.ramirez.campusgps.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AzulClaro,
    onPrimary = AzulContenedorOscuro,
    primaryContainer = AzulContenedorOscuro,
    onPrimaryContainer = TextoClaro,
    secondary = GrisTextoClaro,
    onSecondary = FondoOscuro,
    secondaryContainer = GrisSuperficieOscura,
    onSecondaryContainer = TextoClaro,
    tertiary = AzulClaro,
    onTertiary = AzulContenedorOscuro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = GrisSuperficieOscura,
    onSurfaceVariant = GrisTextoClaro,
    outline = GrisBordeOscuro,
    outlineVariant = GrisSuperficieOscura,
    error = RojoErrorClaro,
    onError = RojoContenedorOscuro,
    errorContainer = RojoContenedorOscuro,
    onErrorContainer = RojoErrorClaro
)

private val LightColorScheme = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = Blanco,
    primaryContainer = AzulSuave,
    onPrimaryContainer = AzulSobreSuave,
    secondary = Negro,
    onSecondary = Blanco,
    secondaryContainer = AzulSuave,
    onSecondaryContainer = AzulSobreSuave,
    tertiary = AzulPrincipal,
    onTertiary = Blanco,
    background = Blanco,
    onBackground = Negro,
    surface = Blanco,
    onSurface = Negro,
    surfaceVariant = GrisSuperficie,
    onSurfaceVariant = GrisTexto,
    outline = GrisTexto,
    outlineVariant = GrisBorde,
    error = RojoError,
    onError = Blanco,
    errorContainer = RojoSuave,
    onErrorContainer = RojoError
)

@Composable
fun CampusgpsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // La paleta del diseno se usa por defecto en todos los dispositivos.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
