package com.ramirez.avisoscampus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Tipografía nativa del sistema: toda la escala está definida, sin valores Material por defecto.
private fun estilo(tamano: Int, altura: Int, peso: FontWeight = FontWeight.Normal) = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = peso,
    fontSize = tamano.sp,
    lineHeight = altura.sp,
    letterSpacing = 0.sp
)

val Typography = Typography(
    displayLarge = estilo(48, 56, FontWeight.ExtraBold),
    displayMedium = estilo(40, 48, FontWeight.ExtraBold),
    displaySmall = estilo(34, 42, FontWeight.ExtraBold),
    headlineLarge = estilo(32, 40, FontWeight.ExtraBold),
    headlineMedium = estilo(28, 36, FontWeight.Bold),
    headlineSmall = estilo(24, 32, FontWeight.Bold),
    titleLarge = estilo(22, 30, FontWeight.Bold),
    titleMedium = estilo(17, 24, FontWeight.SemiBold),
    titleSmall = estilo(16, 24, FontWeight.SemiBold),
    bodyLarge = estilo(17, 26),
    bodyMedium = estilo(16, 24),
    bodySmall = estilo(14, 21),
    labelLarge = estilo(16, 22, FontWeight.SemiBold),
    labelMedium = estilo(14, 20, FontWeight.Medium),
    labelSmall = estilo(12, 18, FontWeight.Medium)
)
