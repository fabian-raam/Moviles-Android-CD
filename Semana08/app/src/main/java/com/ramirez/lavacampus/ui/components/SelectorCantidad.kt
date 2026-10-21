package com.ramirez.lavacampus.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectorCantidad(
    cantidad: Double,
    paso: Double,
    minimo: Double,
    maximo: Double,
    onCantidadChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                val nueva = (cantidad - paso).coerceAtLeast(0.0)
                onCantidadChange(nueva)
            },
            enabled = cantidad > 0.0
        ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar")
        }

        val textoMostrar = if (cantidad % 1.0 == 0.0) {
            cantidad.toInt().toString()
        } else {
            "%.1f".format(cantidad)
        }

        Text(
            text = textoMostrar,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = {
                val nueva = if (cantidad == 0.0) minimo else (cantidad + paso).coerceAtMost(maximo)
                onCantidadChange(nueva)
            },
            enabled = cantidad < maximo
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar")
        }
    }
}