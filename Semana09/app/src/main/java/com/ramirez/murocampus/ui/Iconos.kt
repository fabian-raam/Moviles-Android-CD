package com.ramirez.murocampus.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import com.ramirez.murocampus.ui.theme.Coral

@Composable
private fun IconoDibujado(modifier: Modifier, dibujo: DrawScope.() -> Unit) {
    Canvas(modifier.size(24.dp)) {
        scale(size.width / 24f, size.height / 24f, pivot = Offset.Zero) { dibujo() }
    }
}

@Composable
fun IconoMuro(modifier: Modifier = Modifier, color: Color = Coral) = IconoDibujado(modifier) {
    drawRoundRect(color, Offset(4f, 3f), Size(16f, 7f), CornerRadius(2f), style = Stroke(1.7f))
    drawRoundRect(color, Offset(4f, 14f), Size(16f, 7f), CornerRadius(2f), style = Stroke(1.7f))
}

@Composable
fun IconoNueva(modifier: Modifier = Modifier, color: Color = Coral) = IconoDibujado(modifier) {
    drawCircle(color, 9f, Offset(12f, 12f), style = Stroke(1.7f))
    drawLine(color, Offset(12f, 7f), Offset(12f, 17f), 1.7f, StrokeCap.Round)
    drawLine(color, Offset(7f, 12f), Offset(17f, 12f), 1.7f, StrokeCap.Round)
}

@Composable
fun IconoSinConexion(modifier: Modifier = Modifier, color: Color = Coral) = IconoDibujado(modifier) {
    val ondas = Path().apply {
        moveTo(2f, 8f); cubicTo(7f, 3.5f, 17f, 3.5f, 22f, 8f)
        moveTo(5.5f, 11.5f); cubicTo(9f, 8.5f, 15f, 8.5f, 18.5f, 11.5f)
        moveTo(9f, 15f); cubicTo(11f, 13.5f, 13f, 13.5f, 15f, 15f)
    }
    drawPath(ondas, color, style = Stroke(1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawCircle(color, 1.2f, Offset(12f, 19f))
    drawLine(color, Offset(3f, 2f), Offset(21f, 22f), 1.9f, StrokeCap.Round)
}

@Composable
fun IconoAtras(modifier: Modifier = Modifier, color: Color = Coral) = IconoDibujado(modifier) {
    val flecha = Path().apply { moveTo(15f, 5f); lineTo(8f, 12f); lineTo(15f, 19f) }
    drawPath(flecha, color, style = Stroke(1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

@Composable
fun IconoSiguiente(modifier: Modifier = Modifier, color: Color) = IconoDibujado(modifier) {
    val flecha = Path().apply { moveTo(9f, 6f); lineTo(15f, 12f); lineTo(9f, 18f) }
    drawPath(flecha, color, style = Stroke(1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

@Composable
fun IconoBuscar(modifier: Modifier = Modifier, color: Color) = IconoDibujado(modifier) {
    drawCircle(color, 6.5f, Offset(10f, 10f), style = Stroke(1.8f))
    drawLine(color, Offset(15f, 15f), Offset(21f, 21f), 1.8f, StrokeCap.Round)
}

@Composable
fun IconoExito(modifier: Modifier = Modifier, color: Color) = IconoDibujado(modifier) {
    drawCircle(color, 9f, Offset(12f, 12f), style = Stroke(1.7f))
    val marca = Path().apply { moveTo(8f, 12f); lineTo(11f, 15f); lineTo(16f, 9f) }
    drawPath(marca, color, style = Stroke(1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}
