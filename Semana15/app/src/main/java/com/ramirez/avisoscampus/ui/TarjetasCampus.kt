package com.ramirez.avisoscampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ramirez.avisoscampus.model.PermisoUi
import com.ramirez.avisoscampus.ui.theme.*

@Composable
internal fun TarjetaPermiso(permiso: PermisoUi, mensaje: String?, alPedir: () -> Unit, alAbrirAjustes: () -> Unit) {
    TarjetaCampus("Permiso de notificaciones", IconosCampus.Campana, Coral) {
        when (permiso) {
            PermisoUi.Concedido -> EtiquetaCampus("Permitido", VerdeSuave, VerdeTexto, IconosCampus.Confirmado)
            PermisoUi.Denegado -> EtiquetaCampus("Denegado", CoralSuave, MaterialTheme.colorScheme.error, IconosCampus.Informacion)
            PermisoUi.SinPedir -> EtiquetaCampus("Sin pedir", FondoCampus, TextoSecundario, IconosCampus.Informacion)
        }
        mensaje?.let {
            Surface(color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.medium) {
                Text(it, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(14.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonCampus("Permitir notificaciones", onClick = alPedir)
            BotonCampus("Abrir ajustes", secundario = true, onClick = alAbrirAjustes)
        }
    }
}

@Composable
internal fun TarjetaAviso(alMostrar: () -> Unit) {
    TarjetaCampus("Avisos", IconosCampus.Aviso, Naranja) {
        Text("Muestra un aviso de entrega registrada.", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
        BotonCampus("Mostrar aviso", onClick = alMostrar)
    }
}

@Composable
internal fun TarjetaRecordatorio(faltan: Int?, alIniciar: () -> Unit, alCancelar: () -> Unit) {
    TarjetaCampus("Recordatorio", IconosCampus.Reloj, Turquesa) {
        if (faltan == null) {
            Text("Sin recordatorio activo", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("$faltan s", color = TurquesaTexto, style = MaterialTheme.typography.headlineLarge)
                    Text("restantes", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 4.dp))
                }
                LinearProgressIndicator(
                    progress = { (faltan / 10f).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(5.dp)
                        .semantics { contentDescription = "Faltan $faltan s" },
                    color = Turquesa, trackColor = BordeSuave, gapSize = 0.dp,
                    drawStopIndicator = { }
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonCampus("Recordar en 10 s", enabled = faltan == null, onClick = alIniciar)
            BotonCampus("Cancelar", secundario = true, enabled = faltan != null, onClick = alCancelar)
        }
    }
}

@Composable
internal fun TarjetasReceptores(avion: Boolean, cargador: Boolean, alCambiarAvion: (Boolean) -> Unit, alCambiarCargador: (Boolean) -> Unit) {
    TarjetaCampus("Receptor de anuncios", IconosCampus.Avion, AzulReceptor) {
        FilaReceptor("Escuchar el modo avión", avion, alCambiarAvion)
    }
    TarjetaCampus("Cargador", IconosCampus.Cargador, Verde) {
        FilaReceptor("Escuchar el cargador", cargador, alCambiarCargador)
    }
}

@Composable
private fun FilaReceptor(texto: String, activo: Boolean, alCambiar: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(texto, modifier = Modifier.weight(1f), color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = activo, onCheckedChange = alCambiar,
            modifier = Modifier.semantics { contentDescription = texto },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White, checkedTrackColor = Verde, checkedBorderColor = Verde,
                uncheckedThumbColor = Color.White, uncheckedTrackColor = Color(0xFFD7D8E0), uncheckedBorderColor = Color(0xFFD7D8E0)
            )
        )
    }
}
