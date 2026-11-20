package com.ramirez.centrotareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.ramirez.centrotareas.model.PrimosUi
import com.ramirez.centrotareas.model.CargaUi
import com.ramirez.centrotareas.model.TiempoCarga
import com.ramirez.centrotareas.ui.theme.CentrotareasTheme
import com.ramirez.centrotareas.viewmodel.TareasViewModel

@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()
    val primos by viewModel.primos.collectAsState()
    val carga by viewModel.carga.collectAsState()
    val simularError by viewModel.simularError.collectAsState()
    val aplicarTiempoMaximo by viewModel.aplicarTiempoMaximo.collectAsState()
    val tiempos by viewModel.tiempos.collectAsState()
    ContenidoTareas(
        mensaje, progreso, corriendo,
        onTareaLarga = viewModel::tareaLarga,
        onCancelar = viewModel::cancelar,
        primos = primos,
        onContar = viewModel::contar,
        onCancelarPrimos = viewModel::cancelarPrimos,
        carga = carga,
        onCargarUnaPorUna = viewModel::cargarUnaPorUna,
        onCargarALaVez = viewModel::cargarALaVez,
        simularError = simularError,
        onCambiarSimularError = viewModel::cambiarSimularError,
        aplicarTiempoMaximo = aplicarTiempoMaximo,
        onCambiarTiempoMaximo = viewModel::cambiarAplicarTiempoMaximo,
        tiempos = tiempos,
        onLimpiarTiempos = viewModel::limpiarTiempos
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContenidoTareas(
    mensaje: String,
    progreso: Float,
    corriendo: Boolean,
    onTareaLarga: () -> Unit,
    onCancelar: () -> Unit,
    primos: PrimosUi,
    onContar: (Int) -> Unit,
    onCancelarPrimos: () -> Unit,
    carga: CargaUi,
    onCargarUnaPorUna: () -> Unit,
    onCargarALaVez: () -> Unit,
    simularError: Boolean,
    onCambiarSimularError: (Boolean) -> Unit,
    aplicarTiempoMaximo: Boolean,
    onCambiarTiempoMaximo: (Boolean) -> Unit,
    tiempos: List<TiempoCarga>,
    onLimpiarTiempos: () -> Unit
) {
    var toques by rememberSaveable { mutableStateOf(0) }
    var hasta by rememberSaveable { mutableStateOf("500000") }
    var seccion by rememberSaveable { mutableStateOf(0) }
    val scrollProgreso = rememberScrollState()
    val scrollPrimos = rememberScrollState()
    val scrollCargas = rememberScrollState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    .navigationBarsPadding().clip(RoundedCornerShape(32.dp)),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                tonalElevation = 0.dp,
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                listOf("Progreso", "Primos", "Cargas").forEachIndexed { indice, titulo ->
                    NavigationBarItem(
                        selected = seccion == indice,
                        onClick = { seccion = indice },
                        icon = {
                            Text(listOf("◌", "#", "ϟ")[indice],
                                fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        },
                        label = { Text(titulo) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .verticalScroll(when (seccion) {
                    0 -> scrollProgreso
                    1 -> scrollPrimos
                    else -> scrollCargas
                }).padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (seccion == 0) {
            TarjetaTarea("Tarea con progreso") {
                Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onTareaLarga, enabled = !corriendo) { Text("Iniciar") }
                    OutlinedButton(onClick = onCancelar, enabled = corriendo) { Text("Cancelar") }
                }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${(progreso * 100).toInt()} %")
                Text(mensaje)
                Text("Tócame sigue respondiendo mientras avanza la barra.",
                    style = MaterialTheme.typography.bodySmall)
            }
            }
            if (seccion == 1) {
            TarjetaTarea("Contar números primos") {
                OutlinedTextField(
                    value = hasta,
                    onValueChange = { valor ->
                        if (valor.all { it.isDigit() }) hasta = valor
                    },
                    label = { Text("Contar primos hasta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                val calculando = primos is PrimosUi.Calculando
                val limite = hasta.toIntOrNull()
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { limite?.takeIf { it >= 2 }?.let(onContar) },
                        enabled = !calculando && limite != null && limite >= 2
                    ) { Text("Contar") }
                    OutlinedButton(onClick = onCancelarPrimos, enabled = calculando) {
                        Text("Cancelar")
                    }
                }
                when (primos) {
                    PrimosUi.Inicial -> Text("Ingresa un número y pulsa Contar.")
                    is PrimosUi.Calculando -> {
                        LinearProgressIndicator(
                            progress = { primos.progreso },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Calculando... ${(primos.progreso * 100).toInt()} %")
                    }
                    PrimosUi.Cancelado -> Text("Cancelado")
                    is PrimosUi.Listo -> Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Primos: ${primos.cantidad}")
                            Text("Tiempo: ${primos.milisegundos} ms")
                        }
                    }
                }
            }
            }
            if (seccion == 2) {
            TarjetaTarea("Dos cargas") {
                val cargando = carga is CargaUi.Cargando
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Switch(
                        checked = aplicarTiempoMaximo,
                        onCheckedChange = onCambiarTiempoMaximo,
                        enabled = !cargando
                    )
                    Text("Aplicar tiempo máximo (2 s)")
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Switch(checked = simularError, onCheckedChange = onCambiarSimularError)
                    Text("Simular error")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onCargarUnaPorUna, enabled = !cargando) {
                        Text("Una por una")
                    }
                    Button(onClick = onCargarALaVez, enabled = !cargando) { Text("A la vez") }
                }
                when (carga) {
                    CargaUi.Inicial -> Text("Elige cómo cargar el perfil y los pedidos.")
                    CargaUi.Cargando -> {
                        CircularProgressIndicator()
                        Text("Cargando...")
                    }
                    is CargaUi.Exito -> Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Modo: ${carga.modo}")
                            Text("Perfil: ${carga.perfil}")
                            Text("Pedidos: ${carga.pedidos}")
                            Text("Tiempo: ${carga.milisegundos} ms")
                        }
                    }
                    is CargaUi.Error -> Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(carga.mensaje)
                            OutlinedButton(onClick = onCargarALaVez) { Text("Reintentar") }
                        }
                    }
                }
            }
            TarjetaTarea("Comparación de tiempos") {
                Text("Desactiva el tiempo máximo y el error simulado para registrar cargas completas.")
                if (tiempos.isEmpty()) {
                    Text("Todavía no hay mediciones.")
                } else {
                    Row(Modifier.fillMaxWidth()) {
                        Text("N.º", Modifier.weight(0.5f))
                        Text("Modo", Modifier.weight(1.5f))
                        Text("Tiempo (ms)", Modifier.weight(1f))
                    }
                    HorizontalDivider()
                    tiempos.forEachIndexed { indice, tiempo ->
                        Row(Modifier.fillMaxWidth()) {
                            Text("${indice + 1}", Modifier.weight(0.5f))
                            Text(tiempo.modo, Modifier.weight(1.5f))
                            Text("${tiempo.milisegundos}", Modifier.weight(1f))
                        }
                    }
                    HorizontalDivider()
                    val secuenciales = tiempos.filter { it.modo == "Una por una" }
                    val paralelos = tiempos.filter { it.modo == "A la vez" }
                    val promedioSecuencial = secuenciales.map { it.milisegundos }.average()
                    val promedioParalelo = paralelos.map { it.milisegundos }.average()
                    if (secuenciales.isNotEmpty()) {
                        Text("Promedio una por una: ${promedioSecuencial.toLong()} ms (${secuenciales.size} cargas)")
                    }
                    if (paralelos.isNotEmpty()) {
                        Text("Promedio a la vez: ${promedioParalelo.toLong()} ms (${paralelos.size} cargas)")
                    }
                    if (secuenciales.isNotEmpty() && paralelos.isNotEmpty()) {
                        Text("Diferencia de promedios: ${(promedioSecuencial - promedioParalelo).toLong()} ms")
                    }
                    OutlinedButton(onClick = onLimpiarTiempos) { Text("Limpiar tabla") }
                }
                Text("Las mediciones se conservan al rotar; se reinician al cerrar la pantalla.",
                    style = MaterialTheme.typography.bodySmall)
            }
            }
        }
    }
}

@Composable
private fun TarjetaTarea(titulo: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaTareasPreview() {
    CentrotareasTheme {
        ContenidoTareas(
            "Listo", 0f, false, onTareaLarga = {}, onCancelar = {},
            primos = PrimosUi.Inicial, onContar = {}, onCancelarPrimos = {},
            carga = CargaUi.Inicial, onCargarUnaPorUna = {}, onCargarALaVez = {},
            simularError = false, onCambiarSimularError = {},
            aplicarTiempoMaximo = true, onCambiarTiempoMaximo = {},
            tiempos = emptyList(), onLimpiarTiempos = {}
        )
    }
}
