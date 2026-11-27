package com.ramirez.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ramirez.avisoscampus.model.PermisoUi
import com.ramirez.avisoscampus.notificacion.Notificador
import com.ramirez.avisoscampus.receiver.ModoAvionReceiver
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val permiso by vm.permiso.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(vm) {
        if (Notificador.tienePermiso(contexto)) {
            vm.permisoRespondido(true)
        }
    }

    // Ejemplo resuelto: el ViewModel emite y la View interactúa con Android.
    LaunchedEffect(vm) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // TODO Paso 8: si !mostrado, llamar a vm.noSePudoMostrar().
        }
    }

    DisposableEffect(contexto, escuchando, vm) {
        if (escuchando) {
            val receptor = ModoAvionReceiver { activo -> vm.alCambiarModoAvion(activo) }
            ContextCompat.registerReceiver(
                contexto,
                receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

    // TODO Paso 7: observar vm.avisos y pasarlos a HistorialAvisos.
    // TODO Paso 8: observar vm.mensaje y mostrar el recuadro de error.

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaAvisos("Permiso de notificaciones") {
                val (etiqueta, color) = when (permiso) {
                    PermisoUi.SinPedir -> "Sin pedir" to Color(0xFF616161)
                    PermisoUi.Concedido -> "Permitido" to Color(0xFF237A3B)
                    PermisoUi.Denegado -> "Denegado" to Color(0xFFB3261E)
                }
                Text(
                    text = etiqueta,
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .background(color, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            vm.permisoRespondido(true)
                        }
                    }
                ) { Text("Permitir notificaciones") }
                // TODO Paso 8: agregar el botón Abrir ajustes.
            }

            TarjetaAvisos("Avisos") {
                Text("Muestra un aviso de entrega registrada.")
                Button(
                    onClick = { vm.avisarAhora() }
                ) { Text("Mostrar aviso") }
            }

            TarjetaAvisos("Recordatorio") {
                Text(faltan?.let { "Faltan $it s" } ?: "Sin recordatorio activo")
                Button(
                    onClick = { vm.recordarEn() },
                    enabled = faltan == null
                ) { Text("Recordar en 10 s") }
                OutlinedButton(
                    onClick = { vm.cancelarRecordatorio() },
                    enabled = faltan != null
                ) { Text("Cancelar") }
            }

            TarjetaAvisos("Receptor de anuncios") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text("Escuchar el modo avión", modifier = Modifier.weight(1f))
                    Switch(checked = escuchando, onCheckedChange = { vm.cambiarEscucha(it) })
                }
            }

            // TODO Paso 7: crear HistorialAvisos.kt y reemplazar esta tarjeta.
            TarjetaAvisos("Historial") {
                Text("Sin avisos todavía")
            }
        }
    }
}

@Composable
private fun TarjetaAvisos(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            contenido()
        }
    }
}
