package com.ramirez.avisoscampus.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Tema claro intencional, igual que la referencia, también cuando Android usa modo oscuro.
// Los colores dinámicos del dispositivo no sustituyen la identidad de AvisosCampus.
private val ColoresCampus = lightColorScheme(
    primary = Coral, onPrimary = Color.White,
    primaryContainer = CoralSuave, onPrimaryContainer = Color(0xFFCF211B),
    secondary = TurquesaTexto, onSecondary = Color.White,
    secondaryContainer = TurquesaSuave, onSecondaryContainer = TurquesaTexto,
    tertiary = VerdeTexto, onTertiary = Color.White,
    tertiaryContainer = VerdeSuave, onTertiaryContainer = VerdeTexto,
    background = FondoCampus, onBackground = Tinta,
    surface = Color.White, onSurface = Tinta,
    surfaceVariant = FondoCampus, onSurfaceVariant = TextoSecundario,
    surfaceTint = Color.White,
    surfaceBright = Color.White, surfaceDim = FondoCampus,
    surfaceContainer = FondoCampus, surfaceContainerLow = Color.White,
    surfaceContainerLowest = Color.White, surfaceContainerHigh = FondoCampus,
    surfaceContainerHighest = BordeSuave,
    outline = GrisIcono, outlineVariant = BordeSuave,
    inverseSurface = Tinta, inverseOnSurface = Color.White, inversePrimary = CoralSuave,
    error = Color(0xFFBA251E), onError = Color.White,
    errorContainer = CoralSuave, onErrorContainer = Color(0xFF94251F),
    scrim = Color.Black
)

@Composable
fun AvisoscampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColoresCampus,
        typography = Typography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(22.dp),
            extraLarge = RoundedCornerShape(28.dp)
        ),
        content = content
    )
}
