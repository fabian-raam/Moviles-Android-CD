package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.components.IconoServicio
import com.ramirez.lavacampus.ui.components.StepperEstado
import com.ramirez.lavacampus.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(
    vm: DetallePedidoViewModel,
    onVolver: () -> Unit
) {
    val pedidoConDetalle by vm.pedido.collectAsState()
    val puedeCancelar by vm.puedeCancelar.collectAsState()

    var mostrarDialogoCancelar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                titulo = "Detalle del Pedido",
                onVolver = onVolver
            )
        }
    ) { padding ->
        val p = pedidoConDetalle?.pedido
        val detalles = pedidoConDetalle?.detalles ?: emptyList()

        if (p != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cabecera con ID y Estado
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Pedido #${p.id}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Registrado el: ${p.fechaRegistro}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        EstadoPedidoChip(estadoString = p.estado)
                    }
                }

                // Stepper de avance
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Estado de tu ropa",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            StepperEstado(estadoActualString = p.estado)
                        }
                    }
                }

                // Casillero asignado
                item {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (p.estado == EstadoPedido.LISTO.name && !p.casillero.isNullOrEmpty()) {
                                Text(
                                    text = "📦 Tu ropa está lista en el Casillero ${p.casillero}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Acércate a la lavandería con tu código para recogerla",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            } else {
                                Text(
                                    text = "Casillero: Se asignará al estar listo",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Lista de Servicios contratados
                item {
                    Text(
                        text = "Servicios solicitados",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(detalles) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconoServicio(icono = item.servicio.icono)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = item.servicio.nombre,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${item.detalle.cantidad} x S/ %.2f".format(item.detalle.precioUnitario),
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Text(
                                text = "S/ %.2f".format(item.detalle.subtotal),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Resumen y Pago
                item {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    if (p.descuento > 0.0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "🏷️ Descuento por volumen (10%):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF16A34A)
                            )
                            Text(
                                text = "-S/ %.2f".format(p.descuento),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF16A34A)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total a pagar:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "S/ %.2f".format(p.total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val estadoPago = if (p.pagado) "✅ Pagado" else "⏳ Pago pendiente (se paga al recoger)"
                    Text(
                        text = estadoPago,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (p.pagado) Color(0xFF16A34A) else Color(0xFFD97706)
                    )
                }

                // Sección de Calificación del Servicio (si ya fue entregado)
                if (p.estado == EstadoPedido.ENTREGADO.name) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (p.calificacion != null && p.calificacion > 0) "¡Gracias por tu opinión! (⭐ ${p.calificacion}/5)" else "¿Qué tal te pareció el servicio?",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ComponenteCalificacionEstrellas(
                                    calificacionActual = p.calificacion,
                                    onCalificar = { estrellas ->
                                        vm.calificarPedido(estrellas)
                                    }
                                )
                            }
                        }
                    }
                }

                // Botón de Cancelación
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { mostrarDialogoCancelar = true },
                        enabled = puedeCancelar,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (puedeCancelar) "Cancelar Pedido" else "Solo se cancela antes de empezar el lavado",
                            color = if (puedeCancelar) MaterialTheme.colorScheme.error else Color.Gray
                        )
                    }
                }
            }
        }
    }

    // Diálogo de Confirmación para Cancelar
    if (mostrarDialogoCancelar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoCancelar = false },
            title = { Text("¿Cancelar pedido?") },
            text = { Text("Esta acción no se puede deshacer. Tu pedido pasará a estar cancelado.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoCancelar = false
                        vm.cancelarPedido {
                            onVolver()
                        }
                    }
                ) {
                    Text("Sí, cancelar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoCancelar = false }) {
                    Text("Volver")
                }
            }
        )
    }
}

@Composable
fun ComponenteCalificacionEstrellas(
    calificacionActual: Int?,
    onCalificar: (Int) -> Unit
) {
    val calificacion = calificacionActual ?: 0
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..5).forEach { estrella ->
            IconButton(
                onClick = { onCalificar(estrella) }
            ) {
                Icon(
                    imageVector = if (estrella <= calificacion) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Calificación $estrella estrellas",
                    tint = if (estrella <= calificacion) Color(0xFFFFB800) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}