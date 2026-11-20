package com.ramirez.centrotareas.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun IconoTarea(tipo: Int, modifier: Modifier = Modifier, seleccionado: Boolean = true) {
    val color = if (seleccionado) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(modifier.size(24.dp)) {
        val u = size.minDimension / 24f
        val trazo = 1.8f * u
        fun linea(x1: Float, y1: Float, x2: Float, y2: Float) {
            drawLine(color, Offset(x1 * u, y1 * u), Offset(x2 * u, y2 * u), trazo, StrokeCap.Round)
        }
        when (tipo) {
            0 -> {
                drawArc(color, -90f, 285f, false, Offset(3f * u, 3f * u),
                    Size(18f * u, 18f * u), style = Stroke(trazo, cap = StrokeCap.Round))
                linea(12f, 6f, 12f, 12f)
                linea(12f, 12f, 16f, 14f)
            }
            1 -> {
                linea(9f, 4f, 7f, 20f); linea(17f, 4f, 15f, 20f)
                linea(4f, 9f, 21f, 9f); linea(3f, 15f, 20f, 15f)
            }
            2 -> {
                val path = Path().apply {
                    moveTo(14f * u, 2f * u); lineTo(5f * u, 13f * u)
                    lineTo(11f * u, 13f * u); lineTo(10f * u, 22f * u)
                    lineTo(20f * u, 10f * u); lineTo(14f * u, 10f * u); close()
                }
                drawPath(path, color)
            }
            else -> {
                drawCircle(color, 9f * u, style = Stroke(trazo))
                drawCircle(color, 3f * u)
            }
        }
    }
}
