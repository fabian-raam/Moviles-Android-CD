package com.ramirez.avisoscampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramirez.avisoscampus.model.Aviso
import com.ramirez.avisoscampus.model.Origen
import com.ramirez.avisoscampus.ui.theme.*

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    TarjetaCampus("Historial", IconosCampus.Historial, GrisIcono) {
        if (avisos.isEmpty()) {
            Text("Sin avisos todavía", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
        } else {
            avisos.take(20).forEachIndexed { indice, aviso ->
                if (indice > 0) HorizontalDivider(color = BordeSuave)
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        val (fondo, tinta) = when (aviso.origen) {
                            Origen.BOTON -> CoralSuave to MaterialTheme.colorScheme.onPrimaryContainer
                            Origen.RECORDATORIO -> TurquesaSuave to TurquesaTexto
                            Origen.MODO_AVION -> TurquesaSuave to TurquesaTexto
                            Origen.CARGADOR -> VerdeSuave to VerdeTexto
                        }
                        EtiquetaCampus(aviso.origen.texto, fondo, tinta)
                        Spacer(Modifier.weight(1f))
                        Text(aviso.hora, style = MaterialTheme.typography.labelSmall, color = TextoSecundario)
                    }
                    Text(aviso.titulo, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(aviso.texto, style = MaterialTheme.typography.bodyMedium, color = TextoSecundario)
                }
            }
        }
    }
}
