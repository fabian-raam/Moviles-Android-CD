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
import com.ramirez.centrotareas.ui.theme.CentrotareasTheme
import com.ramirez.centrotareas.viewmodel.TareasViewModel

@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    ContenidoTareas(mensaje, progreso, onTareaLarga = viewModel::tareaLarga)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContenidoTareas(mensaje: String, progreso: Float, onTareaLarga: () -> Unit) {
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
                Button(onClick = onTareaLarga) { Text("Iniciar") }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${(progreso * 100).toInt()} %")
                Text(mensaje)
                Text("Paso 3: Tócame sigue respondiendo mientras avanza la barra.",
                    style = MaterialTheme.typography.bodySmall)
                // TODO Paso 4: controlar el doble inicio y agregar Cancelar.
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
                // TODO Paso 5: observar primos y conectar cálculo y cancelación.
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {}, enabled = false) { Text("Contar") }
                    OutlinedButton(onClick = {}, enabled = false) { Text("Cancelar") }
                }
                Text("Pendiente: cálculo de primos.",
                    style = MaterialTheme.typography.bodySmall)
            }
            TarjetaTarea("Dos cargas") {
                // TODO Paso 6: observar carga y conectar ambos modos y sus resultados.
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {}, enabled = false) { Text("Una por una") }
                    Button(onClick = {}, enabled = false) { Text("A la vez") }
                }
                Text("Pendiente: cargas simuladas, errores y reintento.",
                    style = MaterialTheme.typography.bodySmall)
                // TODO Paso 7: interruptor Simular error, mensaje y Reintentar.
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
    CentrotareasTheme { ContenidoTareas("Listo", 0f, onTareaLarga = {}) }
}
