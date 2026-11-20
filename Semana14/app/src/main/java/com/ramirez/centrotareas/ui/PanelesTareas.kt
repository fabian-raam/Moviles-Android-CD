package com.ramirez.centrotareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.centrotareas.model.PrimosUi
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun PanelProgreso(
    mensaje: String, progreso: Float, corriendo: Boolean, toques: Int,
    onTocar: () -> Unit, onIniciar: () -> Unit, onCancelar: () -> Unit
) {
    TarjetaPanel {
        CabeceraPanel("Tarea larga", mensaje)
        PorcentajeGrande(progreso)
        BarraPanel(progreso)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onIniciar, enabled = !corriendo, modifier = Modifier.weight(1f).height(48.dp)) {
                Text("Iniciar")
            }
            BotonCancelar(corriendo, onCancelar, Modifier.weight(1f))
        }
    }
    TarjetaPanel {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Insignia(3)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("TOQUES", fontSize = 10.sp, letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("$toques", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
            Button(onClick = onTocar, modifier = Modifier.height(44.dp)) { Text("Tocar") }
        }
    }
    Text("La app sigue respondiendo mientras avanza la tarea.",
        color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp))
}

@Composable
internal fun PanelPrimos(
    hasta: String, onHasta: (String) -> Unit, primos: PrimosUi,
    onContar: (Int) -> Unit, onCancelar: () -> Unit,
    ultimaCantidad: Int?, ultimoTiempo: Long?, limiteEnCurso: String,
    onGuardarLimite: (String) -> Unit
) {
    val calculando = primos is PrimosUi.Calculando
    val limite = hasta.toIntOrNull()
    OutlinedTextField(
        value = hasta, onValueChange = onHasta,
        label = { Text("Contar hasta") },
        trailingIcon = { Box(Modifier.padding(end = 12.dp)) { Insignia(1) } },
        textStyle = TextStyle(fontSize = 28.sp, lineHeight = 36.sp, fontWeight = FontWeight.SemiBold),
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        enabled = !calculando,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = { limite?.takeIf { it >= 2 }?.let {
                onGuardarLimite(hasta)
                onContar(it)
            } },
            enabled = !calculando && limite != null && limite >= 2,
            modifier = Modifier.weight(1f).height(48.dp)
        ) { Text("Contar") }
        BotonCancelar(calculando, onCancelar, Modifier.weight(1f))
    }
    TarjetaPanel {
        val progreso = when (primos) {
            is PrimosUi.Calculando -> primos.progreso
            is PrimosUi.Listo -> 1f
            else -> 0f
        }
        val estado = when (primos) {
            is PrimosUi.Calculando -> "En curso"
            is PrimosUi.Listo -> "Completado"
            PrimosUi.Cancelado -> "Cancelado"
            PrimosUi.Inicial -> "Listo"
        }
        CabeceraPanel(if (calculando) "Calculando" else "Cálculo de primos", estado)
        PorcentajeGrande(progreso)
        BarraPanel(progreso)
        Text(
            if (calculando) "Contando hasta ${formatearNumero(limiteEnCurso.toLongOrNull() ?: 0)}"
            else if (primos is PrimosUi.Cancelado) "Cálculo cancelado"
            else if (primos is PrimosUi.Listo) "Cálculo completado"
            else "Ingresa un número para comenzar",
            color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
        )
    }
    Text("Último resultado", color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 13.sp, modifier = Modifier.padding(start = 4.dp, top = 8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TarjetaPanel(Modifier.weight(1f)) {
            Text("Primos", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Text(ultimaCantidad?.let { formatearNumero(it.toLong()) } ?: "—",
                fontSize = 24.sp, fontWeight = FontWeight.SemiBold,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        TarjetaPanel(Modifier.weight(1f)) {
            Text("Tiempo", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Text(ultimoTiempo?.let { formatearNumero(it) } ?: "—",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 24.sp, fontWeight = FontWeight.SemiBold,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("milisegundos", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun formatearNumero(numero: Long): String =
    NumberFormat.getIntegerInstance(Locale.US).format(numero).replace(',', ' ')

@Composable
private fun TarjetaPanel(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)),
        color = MaterialTheme.colorScheme.surface) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
    }
}

@Composable
private fun Insignia(tipo: Int) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(10.dp)) {
        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
            IconoTarea(tipo, Modifier.size(20.dp))
        }
    }
}

@Composable
private fun CabeceraPanel(titulo: String, estado: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Insignia(0)
            Text(titulo, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(20.dp)) {
                Text(estado, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp))
            }
        }
    }
}

@Composable
private fun PorcentajeGrande(progreso: Float) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text("${(progreso.coerceIn(0f, 1f) * 100).toInt()}", fontSize = 68.sp,
            lineHeight = 76.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-2).sp)
        Text(" %", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
private fun BarraPanel(progreso: Float) {
    LinearProgressIndicator(progress = { progreso.coerceIn(0f, 1f) },
        modifier = Modifier.fillMaxWidth().height(8.dp),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceContainer,
        gapSize = 0.dp, drawStopIndicator = {})
}

@Composable
private fun BotonCancelar(enabled: Boolean, onClick: () -> Unit, modifier: Modifier) {
    FilledTonalButton(onClick = onClick, enabled = enabled, modifier = modifier.height(48.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )) { Text("Cancelar") }
}
