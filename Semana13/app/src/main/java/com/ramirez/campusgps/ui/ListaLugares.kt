package com.ramirez.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.campusgps.R
import com.ramirez.campusgps.model.LugarConDistancia
import com.ramirez.campusgps.util.formatearDistancia

@Composable
fun ListaLugares(
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    onClick: (Int) -> Unit,
    lugaresDentroDelRadio: Set<Int>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(lugares, key = { it.lugar.id }) { elemento ->
            val lugar = elemento.lugar
            val estaSeleccionado = lugar.id == seleccionado
            val icono = when {
                lugar.id == 3 -> R.drawable.ic_laboratorio
                lugar.categoria == "Estudio" -> R.drawable.ic_estudio
                lugar.categoria == "Comida" -> R.drawable.ic_comida
                lugar.categoria == "Eventos" -> R.drawable.ic_eventos
                else -> R.drawable.ic_servicios
            }

            // LISTA REDISENADA: filas claras y compactas con icono y distancia a la derecha.
            Card(
                onClick = { onClick(lugar.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (estaSeleccionado) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                ),
                border = if (estaSeleccionado) {
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                } else {
                    null
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 64.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = if (lugar.categoria == "Comida") {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        contentColor = if (lugar.categoria == "Comida") {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(icono),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = lugar.nombre,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = lugar.categoria,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (lugar.id in lugaresDentroDelRadio) {
                            Text(
                                text = "Dentro del radio",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    val metros = elemento.metros
                    if (metros == null) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ) {
                            Text(
                                text = "Sin calcular",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                        }
                    } else {
                        Text(
                            text = formatearDistancia(metros),
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
