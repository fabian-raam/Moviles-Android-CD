package com.ramirez.avisoscampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ramirez.avisoscampus.ui.theme.*

@Composable
internal fun TarjetaCampus(
    titulo: String,
    icono: ImageVector,
    colorIcono: Color,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(shape = RoundedCornerShape(10.dp), color = colorIcono) {
                    Icon(icono, contentDescription = null, tint = Color.White, modifier = Modifier.padding(7.dp).size(20.dp))
                }
                Text(titulo, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            }
            contenido()
        }
    }
}

@Composable
internal fun BotonCampus(texto: String, secundario: Boolean = false, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (secundario) CoralSuave else Coral,
            contentColor = if (secundario) Color(0xFFD52620) else Color.White,
            disabledContainerColor = FondoCampus,
            disabledContentColor = Color(0xFF9C9FA9)
        )
    ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
}

@Composable
internal fun EtiquetaCampus(texto: String, fondo: Color, tinta: Color, icono: ImageVector? = null) {
    Surface(color = fondo, contentColor = tinta, shape = RoundedCornerShape(50)) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (icono != null) Icon(icono, contentDescription = null, modifier = Modifier.size(16.dp))
            Text(texto, style = MaterialTheme.typography.labelMedium)
        }
    }
}
