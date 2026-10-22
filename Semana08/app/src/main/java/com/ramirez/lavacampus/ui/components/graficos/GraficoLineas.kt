package com.ramirez.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.DatoGrafico

@Composable
fun GraficoLineas(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    colorLinea: Color = Color(0xFF0891B2)
) {
    if (datos.isEmpty()) return

    val maxValor = (datos.maxOfOrNull { it.valor } ?: 1.0).coerceAtLeast(1.0)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val width = size.width
                val height = size.height
                val paddingX = 40f
                val paddingY = 30f
                val usableWidth = width - (paddingX * 2)
                val usableHeight = height - (paddingY * 2)

                val puntos = datos.mapIndexed { index, dato ->
                    val x = if (datos.size == 1) width / 2f else paddingX + (index * (usableWidth / (datos.size - 1)))
                    val y = height - paddingY - ((dato.valor / maxValor) * usableHeight).toFloat()
                    Offset(x, y)
                }

                if (puntos.size > 1) {
                    val path = Path().apply {
                        moveTo(puntos.first().x, puntos.first().y)
                        puntos.drop(1).forEach { offset ->
                            lineTo(offset.x, offset.y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = colorLinea,
                        style = Stroke(width = 6f, cap = StrokeCap.Round)
                    )
                }

                puntos.forEachIndexed { _, pt ->
                    drawCircle(
                        color = Color.White,
                        radius = 12f,
                        center = pt
                    )
                    drawCircle(
                        color = colorLinea,
                        radius = 8f,
                        center = pt
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            datos.forEach { dato ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "S/ %.0f".format(dato.valor),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorLinea
                    )
                    Text(
                        text = dato.etiqueta,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
