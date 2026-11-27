package com.ramirez.avisoscampus.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

/** Vectores propios de Compose, reutilizables y sin paquetes de iconos externos. */
object IconosCampus {
    val Campana = trazo("Campana", "M18 8a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9M10 21h4M12 2V1")
    val Aviso = trazo("Aviso", "M3 10h4l10-5v14L7 14H3zM7 14l2 6h3l-2-5M20 9c1 2 1 4 0 6")
    val Reloj = trazo("Reloj", "M21 12a9 9 0 1 1-18 0a9 9 0 0 1 18 0M12 7v5l3 2")
    val Avion = trazo("Avion", "M12 2l2 1v6l7 5v2l-7-2v5l3 2v1l-5-1l-5 1v-1l3-2v-5l-7 2v-2l7-5V3z")
    val Cargador = trazo("Cargador", "M13 2L4 14h7l-1 8l10-13h-7z")
    val Historial = trazo("Historial", "M9 6h12M9 12h12M9 18h12M3 6h1M3 12h1M3 18h1")
    val Confirmado = trazo("Confirmado", "M21 12a9 9 0 1 1-18 0a9 9 0 0 1 18 0M8 12l3 3l5-6")
    val Informacion = trazo("Informacion", "M21 12a9 9 0 1 1-18 0a9 9 0 0 1 18 0M12 11v6M12 7v.1")

    private fun trazo(nombre: String, datos: String) = ImageVector.Builder(
        name = nombre, defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).addPath(
        pathData = PathParser().parsePathString(datos).toNodes(),
        fill = null,
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 1.7f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ).build()
}
