package com.ramirez.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.ramirez.avisoscampus.notificacion.Notificador
import com.ramirez.avisoscampus.receiver.CargadorReceiver
import com.ramirez.avisoscampus.receiver.ModoAvionReceiver
import com.ramirez.avisoscampus.ui.theme.Coral
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel

@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val permiso by vm.permiso.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val escuchandoCargador by vm.escuchandoCargador.collectAsState()
    val avisos by vm.avisos.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    var seccion by rememberSaveable { mutableStateOf(SeccionCampus.Avisos) }
    val scrollAvisos = rememberScrollState()
    val scrollReceptores = rememberScrollState()
    val scrollHistorial = rememberScrollState()
    val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        vm.permisoRespondido(it)
    }

    BackHandler(enabled = seccion != SeccionCampus.Avisos) { seccion = SeccionCampus.Avisos }
    LaunchedEffect(vm) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }
    // La escucha de eventos vive por encima de las pestañas; funciona en cualquier sección.
    LaunchedEffect(vm) {
        vm.eventos.collect { aviso ->
            if (!Notificador.mostrar(contexto, aviso)) vm.noSePudoMostrar()
        }
    }
    LaunchedEffect(faltan != null) {
        if (faltan != null && seccion == SeccionCampus.Avisos) scrollAvisos.animateScrollTo(0)
    }

    // Cambiar de sección no cancela los receptores. Al salir de la pantalla se retiran ambos.
    DisposableEffect(contexto, escuchando, vm) {
        if (escuchando) {
            val receptor = ModoAvionReceiver(vm::alCambiarModoAvion)
            ContextCompat.registerReceiver(contexto, receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED), ContextCompat.RECEIVER_NOT_EXPORTED)
            onDispose { contexto.unregisterReceiver(receptor) }
        } else { onDispose { } }
    }
    DisposableEffect(contexto, escuchandoCargador, vm) {
        if (escuchandoCargador) {
            val receptor = CargadorReceiver(vm::alCambiarCargador)
            val filtro = IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
            ContextCompat.registerReceiver(contexto, receptor, filtro, ContextCompat.RECEIVER_NOT_EXPORTED)
            onDispose { contexto.unregisterReceiver(receptor) }
        } else { onDispose { } }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(Modifier.fillMaxWidth().statusBarsPadding(), contentAlignment = Alignment.Center) {
                Box(Modifier.widthIn(max = 680.dp).fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 16.dp)) {
                    Text(
                        buildAnnotatedString {
                            append("Avisos")
                            withStyle(SpanStyle(color = Coral)) { append("Campus") }
                        },
                        style = MaterialTheme.typography.headlineLarge.copy(letterSpacing = (-1).sp)
                    )
                }
            }
        },
        bottomBar = { MenuCampus(seccion) { seccion = it } }
    ) { padding ->
        val scroll = when (seccion) {
            SeccionCampus.Avisos -> scrollAvisos
            SeccionCampus.Receptores -> scrollReceptores
            SeccionCampus.Historial -> scrollHistorial
        }
        Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.TopCenter) {
            ContenidoSeccion(seccion, scroll) {
                when (seccion) {
                    SeccionCampus.Avisos -> {
                        val orden = if (faltan != null) listOf(TipoTarjeta.Recordatorio, TipoTarjeta.Aviso, TipoTarjeta.Permiso)
                                    else listOf(TipoTarjeta.Permiso, TipoTarjeta.Aviso, TipoTarjeta.Recordatorio)
                        orden.forEach { tarjeta ->
                            key(tarjeta) {
                                when (tarjeta) {
                                    TipoTarjeta.Permiso -> TarjetaPermiso(permiso, mensaje,
                                        alPedir = {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                                lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                                            else vm.permisoRespondido(true)
                                        },
                                        alAbrirAjustes = {
                                            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                                    .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                                            else Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", contexto.packageName, null))
                                            contexto.startActivity(intent)
                                        }
                                    )
                                    TipoTarjeta.Aviso -> TarjetaAviso(vm::avisarAhora)
                                    TipoTarjeta.Recordatorio -> TarjetaRecordatorio(faltan, { vm.recordarEn() }, vm::cancelarRecordatorio)
                                }
                            }
                        }
                    }
                    SeccionCampus.Receptores -> TarjetasReceptores(escuchando, escuchandoCargador, vm::cambiarEscucha, vm::cambiarEscuchaCargador)
                    SeccionCampus.Historial -> HistorialAvisos(avisos)
                }
            }
        }
    }
}

private enum class TipoTarjeta { Permiso, Aviso, Recordatorio }

@Composable
private fun ContenidoSeccion(seccion: SeccionCampus, scroll: ScrollState, contenido: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.widthIn(max = 680.dp).fillMaxSize().testTag("pantalla_${seccion.tag}")
            .verticalScroll(scroll).padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = contenido
    )
}
