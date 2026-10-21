package com.ramirez.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.EstadoPedido

@Composable
fun StepperEstado(
    estadoActualString: String,
    modifier: Modifier = Modifier
) {
    val etapas = listOf(
        EstadoPedido.RECIBIDO,
        EstadoPedido.EN_PROCESO,
        EstadoPedido.LISTO,
        EstadoPedido.ENTREGADO
    )

    val estadoActual = try {
        EstadoPedido.valueOf(estadoActualString)
    } catch (e: Exception) {
        EstadoPedido.RECIBIDO
    }

    val indiceActual = etapas.indexOf(estadoActual).coerceAtLeast(0)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            etapas.forEachIndexed { index, etapa ->
                val activo = index <= indiceActual
                val color = if (activo) MaterialTheme.colorScheme.primary else Color.LightGray

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Canvas(modifier = Modifier.padding(4.dp)) {
                        drawCircle(
                            color = color,
                            radius = if (index == indiceActual) 12f else 8f,
                            center = Offset(0f, 0f)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = etapa.titulo,
                        fontSize = 11.sp,
                        color = if (activo) MaterialTheme.colorScheme.onSurface else Color.Gray
                    )
                }
            }
        }
    }
}