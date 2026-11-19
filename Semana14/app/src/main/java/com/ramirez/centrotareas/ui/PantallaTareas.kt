package com.ramirez.centrotareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
        onCambiarSimularError = viewModel::cambiarSimularError
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
    onCambiarSimularError: (Boolean) -> Unit
) {
    var toques by rememberSaveable { mutableStateOf(0) }
    var hasta by rememberSaveable { mutableStateOf("500000") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                    PrimosUi.Calculando -> {
                        CircularProgressIndicator()
                        Text("Calculando...")
                    }
                    PrimosUi.Cancelado -> Text("Cancelado")
                    is PrimosUi.Listo -> Surface(
                        color = Color(0xFFE0F2E3),
                        contentColor = Color(0xFF1B5E20),
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
            TarjetaTarea("Dos cargas") {
                val cargando = carga is CargaUi.Cargando
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
                        color = Color(0xFFE0F2E3),
                        contentColor = Color(0xFF1B5E20),
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
        }
    }
}

@Composable
private fun TarjetaTarea(titulo: String, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
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
            simularError = false, onCambiarSimularError = {}
        )
    }
}
