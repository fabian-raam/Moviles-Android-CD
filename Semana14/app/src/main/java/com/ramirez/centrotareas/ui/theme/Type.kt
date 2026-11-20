package com.ramirez.centrotareas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private fun estilo(tamano: Int, altura: Int, peso: FontWeight = FontWeight.Normal) = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = peso,
    fontSize = tamano.sp,
    lineHeight = altura.sp,
    letterSpacing = 0.sp,
    fontFeatureSettings = "tnum"
)

val Typography = Typography(
    displayLarge = estilo(76, 84, FontWeight.SemiBold),
    displayMedium = estilo(46, 54, FontWeight.SemiBold),
    displaySmall = estilo(36, 44, FontWeight.SemiBold),
    headlineLarge = estilo(32, 40, FontWeight.SemiBold),
    headlineMedium = estilo(30, 38, FontWeight.SemiBold),
    headlineSmall = estilo(28, 36, FontWeight.SemiBold),
    titleLarge = estilo(26, 34, FontWeight.SemiBold),
    titleMedium = estilo(18, 27, FontWeight.SemiBold),
    titleSmall = estilo(16, 24, FontWeight.Medium),
    bodyLarge = estilo(18, 27),
    bodyMedium = estilo(16, 24),
    bodySmall = estilo(15, 22),
    labelLarge = estilo(16, 24, FontWeight.Medium),
    labelMedium = estilo(14, 20, FontWeight.Medium),
    labelSmall = estilo(13, 19, FontWeight.Medium)
)
