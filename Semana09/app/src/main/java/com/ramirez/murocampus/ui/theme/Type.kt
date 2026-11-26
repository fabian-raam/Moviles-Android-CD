package com.ramirez.murocampus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private fun texto(tamano: Int, alto: Int, peso: FontWeight = FontWeight.Normal) = TextStyle(
    fontFamily = FontFamily.SansSerif, fontWeight = peso,
    fontSize = tamano.sp, lineHeight = alto.sp, letterSpacing = 0.sp
)

val Typography = Typography(
    displayLarge = texto(42, 48, FontWeight.ExtraBold),
    displayMedium = texto(38, 44, FontWeight.ExtraBold),
    displaySmall = texto(36, 42, FontWeight.ExtraBold),
    headlineLarge = texto(30, 37, FontWeight.Bold),
    headlineMedium = texto(28, 35, FontWeight.Bold),
    headlineSmall = texto(26, 33, FontWeight.Bold),
    titleLarge = texto(22, 29, FontWeight.Bold),
    titleMedium = texto(19, 25, FontWeight.SemiBold),
    titleSmall = texto(17, 23, FontWeight.SemiBold),
    bodyLarge = texto(18, 26), bodyMedium = texto(16, 23), bodySmall = texto(14, 20),
    labelLarge = texto(17, 22, FontWeight.SemiBold),
    labelMedium = texto(14, 20, FontWeight.Medium), labelSmall = texto(13, 18, FontWeight.Medium)
)
