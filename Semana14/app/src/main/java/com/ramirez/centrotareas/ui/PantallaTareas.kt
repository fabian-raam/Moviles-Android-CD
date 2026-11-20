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
    var modoParalelo by rememberSaveable { mutableStateOf(true) }
    var ultimaCantidad by rememberSaveable { mutableStateOf<Int?>(null) }
    var ultimoTiempo by rememberSaveable { mutableStateOf<Long?>(null) }
    var limiteEnCurso by rememberSaveable { mutableStateOf(hasta) }
    LaunchedEffect(primos) {
        if (primos is PrimosUi.Listo) {
            ultimaCantidad = primos.cantidad
            ultimoTiempo = primos.milisegundos
        }
    }
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
                            IconoTarea(indice, seleccionado = seccion == indice)
                        },
                        label = { Text(titulo, style = MaterialTheme.typography.labelMedium) },
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
                PanelProgreso(
                    mensaje, progreso, corriendo, toques,
                    onTocar = { toques++ }, onIniciar = onTareaLarga, onCancelar = onCancelar
                )
            }
            if (seccion == 1) {
                PanelPrimos(
                    hasta = hasta,
                    onHasta = { valor -> if (valor.all { it.isDigit() }) hasta = valor },
                    primos = primos, onContar = onContar, onCancelar = onCancelarPrimos,
                    ultimaCantidad = ultimaCantidad, ultimoTiempo = ultimoTiempo,
                    limiteEnCurso = limiteEnCurso, onGuardarLimite = { limiteEnCurso = it }
                )
            }
            if (seccion == 2) {
                PanelCargas(
                    carga = carga, paralelo = modoParalelo, onModo = { modoParalelo = it },
                    limitarTiempo = aplicarTiempoMaximo, onLimitarTiempo = onCambiarTiempoMaximo,
                    simularError = simularError, onSimularError = onCambiarSimularError,
                    onSecuencial = onCargarUnaPorUna, onParalelo = onCargarALaVez,
                    tiempos = tiempos, onLimpiar = onLimpiarTiempos
                )
            }
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
