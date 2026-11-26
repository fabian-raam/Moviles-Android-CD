package com.ramirez.murocampus.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val MuroColores = lightColorScheme(
    primary = Coral, onPrimary = Blanco, primaryContainer = CoralSuave, onPrimaryContainer = TextoPrincipal,
    secondary = Coral, onSecondary = Blanco, secondaryContainer = Segmento, onSecondaryContainer = TextoPrincipal,
    tertiary = VerdeExito, onTertiary = Blanco, tertiaryContainer = FondoExito, onTertiaryContainer = VerdeExito,
    background = Fondo, onBackground = TextoPrincipal,
    surface = Blanco, onSurface = TextoPrincipal, surfaceVariant = Segmento, onSurfaceVariant = TextoSecundario,
    surfaceTint = Coral, inverseSurface = TextoPrincipal, inverseOnSurface = Blanco, inversePrimary = CoralMarca,
    error = Coral, onError = Blanco, errorContainer = CoralSuave, onErrorContainer = TextoPrincipal,
    outline = TextoTenue, outlineVariant = BordeSuave, scrim = TextoPrincipal,
    surfaceBright = Blanco, surfaceDim = Segmento,
    surfaceContainerLowest = Blanco, surfaceContainerLow = Fondo, surfaceContainer = Fondo,
    surfaceContainerHigh = Segmento, surfaceContainerHighest = Deshabilitado
)

// Paleta fija: el sistema y los colores dinámicos no reemplazan el diseño coral.
@Composable
fun MurocampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MuroColores,
        typography = Typography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp), small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(18.dp), large = RoundedCornerShape(24.dp),
            extraLarge = RoundedCornerShape(28.dp)
        ),
        content = content
    )
}
