package com.ramirez.centrotareas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.centrotareas.model.CargaUi
import com.ramirez.centrotareas.model.TiempoCarga

@Composable
internal fun PanelCargas(
    carga: CargaUi, paralelo: Boolean, onModo: (Boolean) -> Unit,
    limitarTiempo: Boolean, onLimitarTiempo: (Boolean) -> Unit,
    simularError: Boolean, onSimularError: (Boolean) -> Unit,
    onSecuencial: () -> Unit, onParalelo: () -> Unit,
    tiempos: List<TiempoCarga>, onLimpiar: () -> Unit
) {
    val cargando = carga is CargaUi.Cargando
    Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("Una por una", "A la vez").forEachIndexed { index, titulo ->
                val seleccionado = paralelo == (index == 1)
                TextButton(
                    onClick = { onModo(index == 1) }, enabled = !cargando,
                    modifier = Modifier.weight(1f).heightIn(min = 52.dp), shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (seleccionado) MaterialTheme.colorScheme.surfaceContainer
                            else MaterialTheme.colorScheme.surface,
                        contentColor = if (seleccionado) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) { Text(titulo, fontWeight = if (seleccionado) FontWeight.SemiBold else FontWeight.Normal) }
            }
        }
    }
    TarjetaCarga {
        OpcionCarga("Límite de 2 s", 0, limitarTiempo, !cargando, onLimitarTiempo)
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        OpcionCarga("Simular error", 2, simularError, !cargando, onSimularError)
    }
    Button(
        onClick = if (paralelo) onParalelo else onSecuencial,
        enabled = !cargando, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
    ) {
        if (cargando) {
            CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
            Spacer(Modifier.width(10.dp))
        }
        Text(if (cargando) "Cargando..." else "Cargar", fontWeight = FontWeight.SemiBold)
    }
    when (carga) {
        CargaUi.Inicial -> Unit
        CargaUi.Cargando -> Text("La pantalla sigue disponible mientras se cargan los datos.",
            color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        is CargaUi.Error -> Surface(
            color = MaterialTheme.colorScheme.errorContainer, shape = RoundedCornerShape(24.dp),
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("No se completó la carga", fontWeight = FontWeight.SemiBold)
                Text(carga.mensaje, style = MaterialTheme.typography.bodyMedium)
                OutlinedButton(onClick = { onModo(true); onParalelo() }) { Text("Reintentar") }
            }
        }
        is CargaUi.Exito -> {
            EtiquetaCarga("Resultado")
            TarjetaCarga {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        EtiquetaCarga("Tiempo")
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("${carga.milisegundos}", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.tertiary)
                            Text(" ms", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 8.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(20.dp)) {
                        Text(carga.modo, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                DatoCarga("Modo", carga.modo)
                DatoCarga("Perfil", carga.perfil)
                DatoCarga("Pedidos", "${carga.pedidos}")
            }
        }
    }
    EtiquetaCarga("Comparación de tiempos")
    TarjetaCarga {
        if (tiempos.isEmpty()) {
            Text("Todavía no hay mediciones", fontWeight = FontWeight.Medium)
            Text("Desactiva el límite de 2 s y el error simulado para completar las cargas.",
                color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        } else {
            val secuenciales = tiempos.filter { it.modo == "Una por una" }
            val paralelos = tiempos.filter { it.modo == "A la vez" }
            val sec = secuenciales.map { it.milisegundos }.average().takeUnless { it.isNaN() } ?: 0.0
            val par = paralelos.map { it.milisegundos }.average().takeUnless { it.isNaN() } ?: 0.0
            val maximo = maxOf(sec, par, 1.0)
            BarraComparacion("Una por una", sec, maximo, secuenciales.size, false)
            BarraComparacion("A la vez", par, maximo, paralelos.size, true)
            if (secuenciales.isNotEmpty() && paralelos.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                DatoCarga("Diferencia de promedios", "${(sec - par).toLong()} ms", destacado = true)
            }
            Text("Historial de cargas", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
            tiempos.forEachIndexed { index, tiempo ->
                DatoCarga("${index + 1}. ${tiempo.modo}", "${tiempo.milisegundos} ms")
            }
            TextButton(onClick = onLimpiar, modifier = Modifier.align(Alignment.End)) { Text("Limpiar tabla") }
        }
    }
}

@Composable
private fun TarjetaCarga(content: @Composable ColumnScope.() -> Unit) {
    Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f))) {
        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
    }
}

@Composable
private fun OpcionCarga(titulo: String, icono: Int, checked: Boolean, enabled: Boolean, onChecked: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        IconoTarea(icono, Modifier.size(26.dp))
        Text(titulo, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onChecked, enabled = enabled)
    }
}

@Composable
private fun EtiquetaCarga(texto: String) {
    Text(texto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun DatoCarga(nombre: String, valor: String, destacado: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(nombre, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(valor, modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodySmall,
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
            fontWeight = FontWeight.SemiBold,
            color = if (destacado) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun BarraComparacion(nombre: String, promedio: Double, maximo: Double, cantidad: Int, naranja: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        DatoCarga(nombre, if (cantidad == 0) "Sin datos" else "${promedio.toLong()} ms")
        LinearProgressIndicator(progress = { (promedio / maximo).toFloat().coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth().height(6.dp), gapSize = 0.dp, drawStopIndicator = {},
            color = if (naranja) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            trackColor = MaterialTheme.colorScheme.surfaceContainer)
        Text("Promedio · $cantidad cargas", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
