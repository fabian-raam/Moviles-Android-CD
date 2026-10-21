package com.ramirez.lavacampus.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramirez.lavacampus.model.EstadoPedido

@Composable
fun EstadoPedidoChip(
    estadoString: String
) {
    val estadoEnum = try {
        EstadoPedido.valueOf(estadoString)
    } catch (e: Exception) {
        EstadoPedido.RECIBIDO
    }

    val (fundo, textoColor) = when (estadoEnum) {
        EstadoPedido.RECIBIDO -> Color(0xFFFEF3C7) to Color(0xFFD97706)
        EstadoPedido.EN_PROCESO -> Color(0xFFDBEAFE) to Color(0xFF2563EB)
        EstadoPedido.LISTO -> Color(0xFFDCFCE7) to Color(0xFF16A34A)
        EstadoPedido.ENTREGADO -> Color(0xFFF1F5F9) to Color(0xFF475569)
        EstadoPedido.CANCELADO -> Color(0xFFFEE2E2) to Color(0xFFDC2626)
    }

    AssistChip(
        onClick = { },
        label = { Text(text = estadoEnum.titulo, color = textoColor) },
        colors = AssistChipDefaults.assistChipColors(containerColor = fundo),
        shape = RoundedCornerShape(16.dp)
    )
}