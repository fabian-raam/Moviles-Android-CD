package com.ramirez.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.campusgps.R
import com.ramirez.campusgps.model.TipoError
import com.ramirez.campusgps.model.UbicacionUi
import com.ramirez.campusgps.ui.theme.Blanco
import com.ramirez.campusgps.ui.theme.Negro

@Composable
fun MensajeError(
    error: UbicacionUi.Error,
    onAbrirAjustes: () -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // MENSAJE REDISENADO: franja de error con texto e icono y accion a la derecha.
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .semantics { liveRegion = LiveRegionMode.Polite },
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_aviso),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = error.mensaje,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
            if (error.tipo != TipoError.SIN_PLAY_SERVICES) {
                Button(
                    onClick = if (error.tipo == TipoError.SIN_PERMISO) {
                        onAbrirAjustes
                    } else {
                        onReintentar
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Negro,
                        contentColor = Blanco
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (error.tipo == TipoError.SIN_PERMISO) {
                            "Abrir ajustes"
                        } else {
                            "Reintentar"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
