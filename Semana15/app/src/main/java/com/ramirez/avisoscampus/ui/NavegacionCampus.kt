package com.ramirez.avisoscampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.avisoscampus.ui.theme.*

internal enum class SeccionCampus(val etiqueta: String, val icono: ImageVector, val tag: String) {
    Avisos("Avisos", IconosCampus.Campana, "avisos"),
    Receptores("Receptores", IconosCampus.Avion, "receptores"),
    Historial("Historial", IconosCampus.Historial, "historial")
}

@Composable
internal fun MenuCampus(actual: SeccionCampus, alSeleccionar: (SeccionCampus) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column {
            HorizontalDivider(color = BordeSuave)
            Row(
                Modifier.fillMaxWidth().navigationBarsPadding().selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SeccionCampus.entries.forEach { seccion ->
                    val seleccionado = actual == seccion
                    val tinta = if (seleccionado) Coral else TextoSecundario
                    Column(
                        modifier = Modifier.weight(1f)
                            .testTag("tab_${seccion.tag}")
                            .selectable(selected = seleccionado, role = Role.Tab, onClick = { alSeleccionar(seccion) })
                            .heightIn(min = 72.dp).padding(horizontal = 4.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                    ) {
                        Icon(seccion.icono, contentDescription = null, tint = tinta, modifier = Modifier.size(25.dp))
                        Text(seccion.etiqueta, color = tinta, style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp))
                    }
                }
            }
        }
    }
}
